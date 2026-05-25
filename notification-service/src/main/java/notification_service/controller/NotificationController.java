package notification_service.controller;

import lombok.RequiredArgsConstructor;
import notification_service.dto.ContactInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final ContactInfoDto contactInfoDto;

    @GetMapping("/contact-info")
    public ResponseEntity<?> contactInfo(){
        return ResponseEntity.ok(contactInfoDto);
    }
}
