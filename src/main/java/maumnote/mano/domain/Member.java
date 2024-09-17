package maumnote.mano.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Member extends BaseEntity implements UserDetails {

    @Id
    private String id;
    private String nickname;
    private String profileImage;
    private String introduction;
    private LocalDateTime joinDate;
    private LocalDateTime withdrawalDate;

    public static Member createNewMember() {
        return Member.builder()
                .id(createMemberId())
                .joinDate(LocalDateTime.now())
                .createId("system")
                .updateId("system")
                .build();
    }

    public static String createMemberId() {
        // UUID 생성
        UUID uuid = UUID.randomUUID();

        return uuid.toString(); // 36자리 문자열 형태로

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
