package notification_service.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
@ConfigurationProperties(prefix = "notification")
@Data
public class ContactInfoDto {
    String message;
    Map<String, String> contactDetails;
    List<String> onCallSupport;
}
