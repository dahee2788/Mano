package maumnote.mano.global.login;


import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.repository.MemberGeneralRepository;
import maumnote.mano.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberGeneralRepository memberGeneralRepository;
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MemberGeneral> memberGeneral =  memberGeneralRepository.findByEmail(username);

        if (memberGeneral.isEmpty()) {

            throw new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage());
        }

        MemberGeneral generalAccount = memberGeneral.get();

        // 롤 생성 - 만약 특정 key면 관리자권한 주고, 일반이면 일반 권한 주고 이런건가요?
        // 보통 userDetail을 쓰던데
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return new User(generalAccount.getEmail(), generalAccount.getPassword(), grantedAuthorities);
    }

}
