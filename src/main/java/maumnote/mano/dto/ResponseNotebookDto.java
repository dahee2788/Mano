package maumnote.mano.dto;

import lombok.ToString;
import maumnote.mano.domain.BaseEntity;
import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@ToString(callSuper = true)
public class ResponseNotebookDto extends BaseEntity {
    private Long id;
    private String name;

}
