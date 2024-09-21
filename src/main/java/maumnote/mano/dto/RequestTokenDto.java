package maumnote.mano.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RequestTokenDto {

    @NotBlank(message = "refreshToken은 비어 있을 수 없습니다.")
    private String refreshToken;
}
