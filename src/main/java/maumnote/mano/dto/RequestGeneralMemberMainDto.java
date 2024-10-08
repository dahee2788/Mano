package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원 가입 요청 DTO")
@Getter
@NoArgsConstructor
public class RequestGeneralMemberMainDto {

    @Schema(description = "이메일", example = "test@test.com")
    @NotBlank
    private String email;

    @Schema(description = "비밀번호", example = "test_password")
    @NotBlank
    private String password;

    @Builder
    public RequestGeneralMemberMainDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
