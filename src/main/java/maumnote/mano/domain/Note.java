package maumnote.mano.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import maumnote.mano.dto.RequestNoteDto;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.global.util.SecurityContextUtil;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
public class Note extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long notebookId;
    private String memberId;
    private int emotionScore;
    private String content;
    private LocalDate writeDate;

    public static Note fromRequestDto(RequestNoteDto dto) {
        Member member = SecurityContextUtil.getAuthenticationMember();

        return Note.builder()
                .notebookId(dto.getNotebookId())
                .memberId(member.getId())
                .emotionScore(dto.getEmotionScore())
                .content(dto.getContent())
                .writeDate(ObjectUtils.isEmpty(dto.getWriteDate()) ? LocalDate.now() : dto.getWriteDate())
                .build();
    }

    public static Note fromRequestDto(long id, RequestNoteDto dto) {

        return Note.builder()
                .id(id)
                .emotionScore(dto.getEmotionScore())
                .content(dto.getContent())
                .writeDate(ObjectUtils.isEmpty(dto.getWriteDate()) ? LocalDate.now() : dto.getWriteDate())
                .build();
    }

    public static ResponseNoteDto toSaveResponseDto(Note note) {
        return ResponseNoteDto.builder()
                .id(note.getId())
                .notebookId(note.getNotebookId())
                .memberId(note.getMemberId())
                .build();
    }

    public static ResponseNoteDto toFindResponseDto(Note note, List<NotePhoto> notePhotos) {

        List<String> images = notePhotos.stream().map(NotePhoto::getImage).toList();

        return ResponseNoteDto.builder()
                .id(note.getId())
                .notebookId(note.getNotebookId())
                .memberId(note.getMemberId())
                .emotionScore(note.getEmotionScore())
                .content(note.getContent())
                .writeDate(note.getWriteDate())
                .images(images)
                .createDate(note.getCreateDate())
                .createId(note.getCreateId())
                .updateDate(note.getUpdateDate())
                .updateId(note.getUpdateId())
                .build();
    }

    public static List<ResponseNoteDto> toFindListResponseDto(List<Note> notes) {
        return notes.stream().map(note -> ResponseNoteDto.builder()
                .id(note.getId())
                .notebookId(note.getNotebookId())
                .memberId(note.getMemberId())
                .emotionScore(note.getEmotionScore())
                .content(note.getContent())
                .writeDate(note.getWriteDate())
                .createDate(note.getCreateDate())
                .createId(note.getCreateId())
                .updateDate(note.getUpdateDate())
                .updateId(note.getUpdateId())
                .build()).collect(Collectors.toList());
    }
}
