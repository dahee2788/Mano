package maumnote.mano.global.security;

import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.service.MemberService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
@RequiredArgsConstructor
public class MemberAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = memberService.loadUserByUsername(username);
        // 비밀번호 검증로직
        if (ObjectUtils.isEmpty(userDetails) || !MemberGeneral.matchPassword(password,userDetails.getPassword())) {
            throw new AuthenticationException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()) {};
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
