package auth_service.mapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

public class ErrorMapper {
    private ErrorMapper(){}

    public  static String mapToErrorJson(HttpServletRequest request,String errCode, String errMsg){
        String apiPath = request.getRequestURI();
        String errTime = LocalDateTime.now().toString();

        return String.format(
                "{ \"apiPath\": \"%s\", \"errCode\": \"%s\",\"errMsg\": \"%s\",\"errTime\": \"%s\"}"
                ,apiPath,errCode,errMsg,errTime
        );
    }
}
