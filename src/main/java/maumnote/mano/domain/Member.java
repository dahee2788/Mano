package maumnote.mano.domain;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.dto.ResponseMemberJoinDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class Member extends BaseEntity implements UserDetails {


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    List<MemberRole> memberRoles = new ArrayList<>();
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
        UUID uuid = UUID.randomUUID();
        return uuid.toString();

    }

    public static Member getGeneralVoFromDto(RequestGeneralMemberMainDto requestGeneralMemberMainDto, Role role) {
        String id = createMemberId();

        Member member = Member.builder()
                .id(id)
                .joinDate(LocalDateTime.now())
                .joinType(JoinType.GENERAL)
                .email(requestGeneralMemberMainDto.getEmail())
                .password(encodePassword(requestGeneralMemberMainDto.getPassword()))
                .createId(id)
                .updateId(id)
                .build();

        MemberRole memberRole = MemberRole.builder()
                .role(role)
                .member(member)
                .build();

        member.getMemberRoles().add(memberRole);
        return member;
    }

    public static ResponseMemberJoinDto toResponseDto(Member member) {
        return ResponseMemberJoinDto.builder()
                .id(member.getId())
                .build();
    }

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
        return memberRoles.stream()
                .map(memberRole -> new SimpleGrantedAuthority(memberRole.getRole().getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
