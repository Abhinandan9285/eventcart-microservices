package auth_service.service;

import auth_service.dto.LoginRequest;
import auth_service.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<?> register(RegisterRequest request);
    ResponseEntity<?> login(LoginRequest request);
}