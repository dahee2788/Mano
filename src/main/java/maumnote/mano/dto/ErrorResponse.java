package maumnote.mano.dto;

import lombok.AllArgsConstructor;
import maumnote.mano.exception.ErrorCode;

@AllArgsConstructor
public class ErrorResponse {
    private ErrorCode  errorCode;
    private String  errorMassage;
}
