package auth_service.dto.request;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;
@ConfigurationProperties(prefix = "auth")
public record ContactInfoDto(String message, Map<String, String> contactDetails, List<String> onCallSupport) {
}
