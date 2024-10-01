package maumnote.mano.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import maumnote.mano.domain.BaseEntity;

import java.time.LocalDate;
import java.util.List;

@Getter
@SuperBuilder
public class ResponseNoteDto extends BaseEntity {
    private long id;
    private long notebookId;
    private String memberId;
    private int emotionScore;
    private String content;
    private LocalDate writeDate;
    private List<String> images;
}
