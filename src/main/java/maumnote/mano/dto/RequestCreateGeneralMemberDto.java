package maumnote.mano.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestCreateGeneralMemberDto {

    private String email;
    private String password;

    @Builder
    public RequestCreateGeneralMemberDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
