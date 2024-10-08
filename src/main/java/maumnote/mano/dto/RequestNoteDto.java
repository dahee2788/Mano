package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "일기 작성/수정 요청하는 DTO")
@Getter
@Builder
public class RequestNoteDto {

    @Schema(description = "일기장 id", example = "1234")
    @NotNull(message = "일기장을 선택해주세요.")
    private Long notebookId;

    @Pattern(regexp = "[0-10]")
    @Schema(description = "기분 점수", example = "10")
    @NotNull(message = "기분 점수는 비어있을 수 없습니다.")
    private Integer emotionScore;

    @Schema(description = "일기 내용", example = "오늘 정말 행복한 하루였다.")
    @NotBlank(message = "일기 내용은 비어있을 수 없습니다.")
    private String content;

    @Schema(description = "작성일자, 일기의 날짜와 실제 작성한 날짜가 다르다면 값을 넣어주세요.", example = "yyyyMMdd")
    private LocalDate writeDate;

    @Schema(description = "사진, 이미지의 경로를 List에 담아서 보내주세요.")
    private List<String> photos;
}
