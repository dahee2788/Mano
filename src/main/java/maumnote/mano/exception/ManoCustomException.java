package maumnote.mano.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ManoCustomException extends RuntimeException{
    private ErrorCode errorCode;
    private String message;

    public ManoCustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.getMessage();
    }
}
