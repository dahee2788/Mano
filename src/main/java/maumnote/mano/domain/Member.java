package maumnote.mano.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.dto.ResponseMemberJoinDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    @Enumerated(EnumType.STRING)
    private JoinType joinType;
    private String email;
    private String password;
    private String socialKey;

    public static String createMemberId() {
        // UUID 생성
        UUID uuid = UUID.randomUUID();
        return uuid.toString(); // 36자리 문자열 형태로

    }

    public static Member getGeneralVoFromDto(RequestGeneralMemberMainDto requestGeneralMemberMainDto) {
        String id = createMemberId();
        return Member.builder()
                .id(id)
                .joinDate(LocalDateTime.now())
                .joinType(JoinType.GENERAL)
                .email(requestGeneralMemberMainDto.getEmail())
                .password(encodePassword(requestGeneralMemberMainDto.getPassword()))
                .createId(id)
                .updateId(id)
                .build();
    }

    public static ResponseMemberJoinDto toResponseDto(Member member) {
        return ResponseMemberJoinDto.builder()
                .id(member.getId())
                .build();
    }

    // BCryptPasswordEncoder 사용
    public static String encodePassword(String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);
    }

    public static boolean matchPassword(String password, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(password, encodedPassword);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
