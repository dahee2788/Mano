package maumnote.mano.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member_general")
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MemberGeneral extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;


    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    public static MemberGeneral fromDto(RequestGeneralMemberMainDto requestGeneralMemberMainDto, Member member) {

        return MemberGeneral.builder()
                   .email(requestGeneralMemberMainDto.getEmail())
                   .password(encodePassword(requestGeneralMemberMainDto.getPassword()))
                   .member(member)
                   .createId("system")
                   .updateId("system")
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
//        return this.roles.stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
