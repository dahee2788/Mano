package maumnote.mano.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import maumnote.mano.domain.BaseEntity;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@ToString(callSuper = true)
@NoArgsConstructor
public class ResponseNotebookDto extends BaseEntity {
    private Long id;
    private String name;

}
