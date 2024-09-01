package maumnote.mano.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestGeneralMemberMainDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @Builder
    public RequestGeneralMemberMainDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
