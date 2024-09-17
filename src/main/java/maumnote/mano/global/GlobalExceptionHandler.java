package maumnote.mano.global;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import maumnote.mano.dto.ErrorResponse;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e, HttpServletRequest request) {

        log.error(e.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();

        // 최대 3줄만 출력
        Arrays.stream(stackTrace).limit(3).forEach(stackTraceElement -> log.error(stackTraceElement.toString()));

        log.error("호출 url : {}", request.getRequestURI());

        if (e instanceof ManoCustomException) {

            log.error("ManoCustomException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(((ManoCustomException) e).getErrorCode(), e.getMessage()));
        }
        if (e instanceof MethodArgumentNotValidException) {

            // 에러 메시지 수집
            String errorMessage = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
            log.error("MethodArgumentNotValidException : {}", errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.VALIDATION_ERROR, errorMessage));
        }
        if (e instanceof MethodArgumentTypeMismatchException) {

            StringBuilder errorMessage = new StringBuilder("[" + ((MethodArgumentTypeMismatchException) e).getName() + "] 잘못된 요청입니다.");
            errorMessage.append("\n").append(e.getMessage());
            // 에러 메시지 수집
            log.error("MethodArgumentTypeMismatchException : {}", errorMessage.toString());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.INVALID_FORMAT, errorMessage.toString()));
        }
        if (e instanceof HttpMessageNotReadableException) {

            log.error("HttpMessageNotReadableException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.EMPTY_REQUEST_BODY, e.getMessage()));
        }
        if (e instanceof ExpiredJwtException) {

            log.error("ExpiredJwtException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ErrorCode.EXPIRED_TOKEN, e.getMessage()));
        }
        if (e instanceof AuthenticationException) {

            log.error("AuthenticationException : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ErrorCode.GENERAL_LOGIN_FAIL, e.getMessage()));

        } else {
            log.error("Exception : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.UNKNOWN_ERROR, e.getMessage()));
        }

    }
}
