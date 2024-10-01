package maumnote.mano.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class RequestNoteDto {

    @NotNull(message = "일기장을 선택해주세요.")
    private Long notebookId;
    @NotNull(message = "기분 점수는 비어있을 수 없습니다.")
    private Integer emotionScore;
    @NotBlank(message = "일기 내용은 비어있을 수 없습니다.")
    private String content;
    private LocalDate writeDate;
    private List<String> photos;
}
