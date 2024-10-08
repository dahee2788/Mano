package maumnote.mano.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Schema(description = "데이터 생성일자")
    @CreationTimestamp
    @Column(updatable = false) // 생성 시에만 값을 설정하고, 업데이트 시에는 변경되지 않도록 설정
    private LocalDateTime createDate;
    @Schema(description = "데이터 생성자 id", example = "a7aeda02-a508-44f7-b17b-55b1b7400ac0")
    private String createId;
    @Schema(description = "데이터 수정일자")
    @CreationTimestamp
    private LocalDateTime updateDate;
    @Schema(description = "데이터 수정자 id", example = "a7aeda02-a508-44f7-b17b-55b1b7400ac0")
    private String updateId;
}
