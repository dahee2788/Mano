package maumnote.mano.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "일기장 등록 요청 DTO")
@Getter
@NoArgsConstructor
public class RequestNotebookDto {

    @Schema(description = "일기장 이름", example = "일기장")
    @NotBlank(message = "일기장 이름은 비어 있을 수 없습니다.")
    private String name;

    @Builder
    public RequestNotebookDto(String name) {
        this.name = name;
    }
}
