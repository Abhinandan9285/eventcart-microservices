package auth_service.controller;

import auth_service.dto.request.ContactInfoDto;
import auth_service.dto.request.LoginRequest;
import auth_service.dto.request.RegisterRequest;
import auth_service.service.IAuthService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;
    private final ContactInfoDto contactInfoDto;
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/contact-info")
    public ResponseEntity<?> contactInfo(){
        return ResponseEntity.ok(contactInfoDto);
    }
}