package gateway_service.filter;

import gateway_service.mapper.ErrorMapper;
import gateway_service.service.IJwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class JwtAuthentication extends AbstractGatewayFilterFactory<JwtAuthentication.Config> {
    private final IJwtService jwtService;

    public JwtAuthentication(IJwtService jwtService) {
        super(Config.class);
        this.jwtService = jwtService;
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            String path = exchange.getRequest()
                    .getURI()
                    .getPath();
            log.info("HERERe..................{}",path);
            /*
             * Public APIs
             */
            if (path.contains("/auth/login")
                    || path.contains("/auth/register")
                    || path.contains("/eureka")
                    || path.contains("/actuator")) {

                return chain.filter(exchange);
            }

            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return handleUnauthorized(
                        exchange,
                        "AUTH_HEADER_MISSING",
                        "Authorization Header Is Missing"
                );
            }

            String token = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null) {
                return handleUnauthorized(
                        exchange,
                        "AUTH_TOKEN_MISSING",
                        "Authorization Token Is Missing"
                );
            }

            if (!jwtService.validateToken(token)) {

                return handleUnauthorized(
                        exchange,
                        "INVALID_TOKEN",
                        "JWT Token Is Invalid"
                );
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> handleUnauthorized(
            ServerWebExchange exchange,
            String errCode,
            String errMsg
    ) {

        exchange.getResponse()
                .setStatusCode(HttpStatus.UNAUTHORIZED);

        exchange.getResponse()
                .getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        String json = ErrorMapper.mapToErrorJson(
                exchange,
                errCode,
                errMsg
        );

        DataBuffer buffer = exchange.getResponse()
                .bufferFactory()
                .wrap(json.getBytes(StandardCharsets.UTF_8));

        return exchange.getResponse()
                .writeWith(Mono.just(buffer));
    }

    public static class Config {

    }
}