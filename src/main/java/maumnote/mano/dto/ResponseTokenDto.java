package maumnote.mano.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseTokenDto {
    private String accessToken;
    private String refreshToken;
}
