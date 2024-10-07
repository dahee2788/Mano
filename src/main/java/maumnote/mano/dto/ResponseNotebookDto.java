package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import maumnote.mano.domain.BaseEntity;

@Schema(description = "일기장 저장 응답 DTO")
@Getter
@SuperBuilder
@ToString(callSuper = true)
public class ResponseNotebookDto extends BaseEntity {
    @Schema(description = "일기장 id , 일기장의 고유키입니다.", example = "1234")
    private Long id;

    @Schema(description = "일기장 이름", example = "일기장")
    private String name;

}
