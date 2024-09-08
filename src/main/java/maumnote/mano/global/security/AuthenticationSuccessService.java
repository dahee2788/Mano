package maumnote.mano.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.global.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationSuccessService {

    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    public void handleSuccess(HttpServletResponse response, Authentication authentication) throws IOException {

        // Principal 정보 가져오기
        Object principal = authentication.getPrincipal();
        String memberId = null;

        // 일반 로그인
        if (principal instanceof MemberGeneral) {
            memberId = ((MemberGeneral) principal).getMember().getId();
        }

        String token = tokenProvider.generateToken(memberId,authentication.getAuthorities());
        // 리스폰스 객체에 에러 담기
        ApiResponse<?> apiResponse =  ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.LOGIN_SUCCESS);

        response.setStatus(HttpServletResponse.SC_OK); //
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        response.addHeader(TOKEN_HEADER, TOKEN_PREFIX + token);

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // 직렬화하여 JSON 응답
        response.flushBuffer();  // flush는 특정 출력 스트림에 적용 / flushBuffer는 http 응답 전체에 적용, 응답의 상태 코드와 헤더를 포함
    }
}
