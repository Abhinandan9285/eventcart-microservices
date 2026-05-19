package gateway_service.mapper;

import org.springframework.web.server.ServerWebExchange;

import java.time.LocalDateTime;

public class ErrorMapper {

    private ErrorMapper(){}

    public static String mapToErrorJson(ServerWebExchange exchange, String errCode, String errMsg) {

        String apiPath = exchange.getRequest().getURI().getPath();
        String errTime = LocalDateTime.now().toString();

        return String.format(
                """
                {
                    "apiPath":"%s",
                    "errCode":"%s",
                    "errMsg":"%s",
                    "errTime":"%s"
                }
                """,
                apiPath,
                errCode,
                errMsg,
                errTime
        );
    }
}