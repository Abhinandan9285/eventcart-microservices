package auth_service.dto;

import auth_service.constant.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}