package maumnote.mano.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestNotebookDto {

    @NotBlank(message = "일기장 이름은 비어 있을 수 없습니다.")
    private String name;

    @Builder
    public RequestNotebookDto(String name) {
        this.name = name;
    }
}
