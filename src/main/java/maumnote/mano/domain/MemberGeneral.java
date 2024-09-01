package maumnote.mano.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Table(name = "member_general")
@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MemberGeneral extends BaseEntity{

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


}
