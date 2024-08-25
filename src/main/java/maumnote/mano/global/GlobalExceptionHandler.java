package maumnote.mano.global;

import lombok.extern.slf4j.Slf4j;
import maumnote.mano.dto.ErrorResponse;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ManoCustomException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleManoCustomException(ManoCustomException e) {

        log.error("ManoCustomException : {}", e.getMessage());

        return new ResponseEntity<>(new ErrorResponse(e.getErrorCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // 에러 메시지 수집
        String errorMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return new ResponseEntity<>(new ErrorResponse(ErrorCode.VALIDATION_ERROR, errorMessage), HttpStatus.BAD_REQUEST);
    }
}
