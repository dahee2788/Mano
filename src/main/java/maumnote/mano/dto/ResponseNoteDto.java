package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import maumnote.mano.domain.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "일기 저장 응답 DTO")
@Getter
@SuperBuilder
public class ResponseNoteDto extends BaseEntity {
    @Schema(description = "일기 id, 일기 고유키", example = "12345")
    private long id;

    @Schema(description = "일기장 id, 일기장 고유키", example = "1234")
    private long notebookId;

    @Schema(description = "작성 회원 id, 회원 고유키", example = "a7aeda02-a508-44f7-b17b-55b1b7400ac0")
    private String memberId;

    @Pattern(regexp = "[0-10]")
    @Schema(description = "기분 점수", example = "10")
    private int emotionScore;

    @Schema(description = "일기 내용", example = "정말 즐거운 하루였다.")
    private String content;

    @Schema(description = "일기 작성일자, 사용자가 지정할 수 있기에 실제 데이터 생성일과 다를 수 있습니다.", example = "yyyyMMdd")
    private LocalDate writeDate;

    @Schema(description = "사진 , 이미지 경로가 List에 담겨있습니다.")
    private List<String> images;
}
