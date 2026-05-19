package gateway_service.service;

import io.jsonwebtoken.Claims;

public interface IJwtService {

    Claims extractClaims(String token);

    boolean validateToken(String token);
}