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
import maumnote.mano.global.util.SecurityContextUtil;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class NotebookPermission extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private long notebookId;
    private String memberId;

    public static NotebookPermission from(long notebookId) {

        Member principal = SecurityContextUtil.getAuthenticationMember();

        return NotebookPermission.builder()
                .notebookId(notebookId)
                .memberId(principal.getId())
                .createId(principal.getId())
                .updateId(principal.getId())
                .build();
    }

}
