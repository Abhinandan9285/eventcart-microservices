package auth_service.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ErrorResponseDto {
    private String apiPath;
    private HttpStatus errCode;
    private String errMsg;
    private String errTime;
}
