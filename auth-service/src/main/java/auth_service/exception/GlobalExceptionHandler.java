package auth_service.exception;

import auth_service.dto.ErrorResponseDto;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handlerBadRequestException(BadRequestException ex, WebRequest request){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errMsg(ex.getMessage())
                .errTime(LocalDateTime.now().toString())
                .errCode(HttpStatus.BAD_REQUEST)
                .apiPath(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request){
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errMsg(ex.getMessage())
                .errTime(LocalDateTime.now().toString())
                .errCode(HttpStatus.NOT_FOUND)
                .apiPath(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception ex , WebRequest request){
        log.info("error: {}",ex);
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .errMsg(ex.getMessage())
                .errTime(LocalDateTime.now().toString())
                .errCode(HttpStatus.INTERNAL_SERVER_ERROR)
                .apiPath(request.getDescription(false))
                .build();

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
