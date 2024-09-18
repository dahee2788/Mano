package maumnote.mano.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MemberAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    public MemberAuthenticationFilter(AuthenticationManager authenticationManager) {

        super("/login"); // 로그인 URL 설정
        setAuthenticationManager(authenticationManager);
        this.objectMapper = new ObjectMapper();

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        RequestGeneralMemberMainDto loginRequest = objectMapper.readValue(request.getInputStream(), RequestGeneralMemberMainDto.class);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        return getAuthenticationManager().authenticate(authRequest);
    }

}
