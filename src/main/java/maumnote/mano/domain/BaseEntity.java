package maumnote.mano.domain;

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
    @CreationTimestamp
    @Column(updatable = false) // 생성 시에만 값을 설정하고, 업데이트 시에는 변경되지 않도록 설정
    private LocalDateTime createDate;
    private String         createId;
    @CreationTimestamp
    private LocalDateTime updateDate;
    private String         updateId;
}
