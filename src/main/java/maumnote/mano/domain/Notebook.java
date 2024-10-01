package maumnote.mano.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.global.util.SecurityContextUtil;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Notebook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public static Notebook fromRequestDto(RequestNotebookDto dto) {
        Member principal = SecurityContextUtil.getAuthenticationMember();

        return Notebook.builder()
                .name(dto.getName())
                .createId(principal.getId())
                .updateId(principal.getId())
                .build();
    }

    public static Notebook fromRequestDto(long id, RequestNotebookDto dto) {
        Member principal = SecurityContextUtil.getAuthenticationMember();

        return Notebook.builder()
                .id(id)
                .name(dto.getName())
                .createId(principal.getId())
                .updateId(principal.getId())
                .build();
    }

    public static ResponseNotebookDto toResponseDto(Notebook notebook) {
        return ResponseNotebookDto.builder()
                .id(notebook.getId())
                .name(notebook.getName())
                .createDate(notebook.getCreateDate())
                .createId(notebook.getCreateId())
                .updateDate(notebook.getUpdateDate())
                .updateId(notebook.getUpdateId())
                .build();
    }

}


