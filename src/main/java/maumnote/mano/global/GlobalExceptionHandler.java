package maumnote.mano.global;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.ErrorResponse;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e, Throwable throwable, HttpServletRequest request) {
        // 보통 Throwable 에서는 어떤 값을 출력해보는지?
        ResponseEntity<ErrorResponse> errorResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.UNKNOWN_ERROR,e.getMessage()));
        log.error(e.getMessage());
        log.error(throwable.getMessage());
        StackTraceElement[] stackTrace = e.getStackTrace();

        // 최대 3줄만 출력
        for (int i = 0; i < Math.min(3, stackTrace.length); i++) {
            log.error(String.valueOf(stackTrace[i]));
        }

        log.error("호출 url : {}", request.getRequestURI());

        if( e instanceof ManoCustomException){

            log.error("ManoCustomException : {}", e.getMessage());
            errorResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(((ManoCustomException) e).getErrorCode(),e.getMessage()));
        }
        if( e instanceof MethodArgumentNotValidException){

            // 에러 메시지 수집
            String errorMessage = ((MethodArgumentNotValidException)e).getBindingResult().getAllErrors().get(0).getDefaultMessage();
            log.error("MethodArgumentNotValidException : {}", errorMessage);
            errorResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.VALIDATION_ERROR, errorMessage));
        }
        if( e instanceof MethodArgumentTypeMismatchException){

            StringBuilder errorMessage = new StringBuilder("["+((MethodArgumentTypeMismatchException)e).getName()+"] 잘못된 요청입니다.");
            errorMessage.append("\n"+e.getMessage());
            // 에러 메시지 수집
            log.error("MethodArgumentTypeMismatchException : {}", errorMessage.toString());

            errorResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.INVALID_FORMAT, errorMessage.toString()));
        }
        if( e instanceof NoSuchElementException){

            log.error("NoSuchElementException : {}", e.getMessage());
            errorResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.ELEMENT_NOT_FOUND, e.getMessage()));
        }
        if( e instanceof SQLIntegrityConstraintViolationException){

            log.error("SQLIntegrityConstraintViolationException : {}", e.getMessage());
            errorResponse = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(ErrorCode.DATA_INTEGRITY_ERROR, e.getMessage()));
        }
        if( e instanceof HttpMessageNotReadableException){

            log.error("HttpMessageNotReadableException : {}", e.getMessage());
            errorResponse = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ErrorCode.EMPTY_REQUEST_BODY, e.getMessage()));
        }


        return errorResponse;

    }
}
