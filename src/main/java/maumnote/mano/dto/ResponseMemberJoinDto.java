package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "회원 가입 응답 DTO")
@Getter
@Builder
public class ResponseMemberJoinDto {

    @Schema(description = "회원 고유 키", example = "a7aeda02-a508-44f7-b17b-55b1b7400ac0")
    private String id;
}
