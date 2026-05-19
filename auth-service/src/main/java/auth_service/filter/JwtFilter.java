package auth_service.filter;

import auth_service.mapper.ErrorMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if(jwt != null){
            try {
                SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();

                String role = claims.get("role",String.class);
                String email = claims.getSubject();

                Authentication authentication =  new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        List.of(new SimpleGrantedAuthority(role)));
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }catch (JwtException ex){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");

                String errCode = "UNAUTHORIZED";
                String errMsg = "INVALID JWT TOKEN";

                if(ex instanceof ExpiredJwtException){
                    errMsg = "Your Session Is Expired";
                    errCode = "TOKEN EXPIRED";
                }
                else if(ex instanceof MalformedJwtException){
                    errMsg = "Token Is Malformed";
                    errCode = "INVALID TOKEN";
                }
                else if(ex instanceof SignatureException){
                    errMsg = "Token Signature Is Invalid";
                    errCode = "INVALID TOKEN SIGNATURE";
                }

                String json = ErrorMapper.mapToErrorJson(request,errCode,errMsg);
                response.getWriter().write(json);
                response.getWriter().flush();
                return;
            }
        }

        filterChain.doFilter(request,response);
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();
        return path.equalsIgnoreCase("/auth/login") || path.equalsIgnoreCase("/auth/register");
    }
}
