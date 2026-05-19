package auth_service.service.impl;

import auth_service.service.IJwtService;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
@Slf4j
public class IJwtServiceImpl implements IJwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public String generateToken(Authentication authentication) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String role = authentication.getAuthorities()
                .stream()
                .findFirst()
                .get()
                .getAuthority();

        log.info("role :{}",role);
        return Jwts.builder()
                .subject(authentication.getName())
                .claim("role",role)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + 1000 * 60 * 30))
                .signWith(key)
                .compact();

    }
}
