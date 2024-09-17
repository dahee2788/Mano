package maumnote.mano.global.security;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseLoginDto {
    private String token;
}
