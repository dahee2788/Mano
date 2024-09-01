package maumnote.mano.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Member extends BaseEntity {

    @Id
    private String id;
    private String nickname;
    private String profileImage;
    private String introduction;
    private LocalDateTime joinDate;
    private LocalDateTime withdrawalDate;

    public static Member newMember(){
        return Member.builder()
                .id(createMemberId())
                .joinDate(LocalDateTime.now())
                .createId("system")
                .updateId("system")
                .build();
    }

    public static String createMemberId(){
        // UUID 생성
        UUID uuid = UUID.randomUUID();

       return uuid.toString(); // 36자리 문자열 형태로

    }
}
