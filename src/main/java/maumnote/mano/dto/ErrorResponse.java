package maumnote.mano.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import maumnote.mano.exception.ErrorCode;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private ErrorCode  errorCode;
    private String  errorMassage;
}
