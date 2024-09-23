package maumnote.mano.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Member;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.ResponseTokenDto;
import maumnote.mano.global.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        Object principal = authentication.getPrincipal();
        String memberId = ((Member) principal).getId();

        ResponseTokenDto token = tokenProvider.getTokenResponse(memberId, authentication.getAuthorities());
        ApiResponse<?> apiResponse = ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.LOGIN_SUCCESS, token);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        response.flushBuffer();  // flush는 특정 출력 스트림에 적용 / flushBuffer는 http 응답 전체에 적용, 응답의 상태 코드와 헤더를 포함
    }
}
