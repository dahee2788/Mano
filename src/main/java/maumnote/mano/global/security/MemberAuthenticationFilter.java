package maumnote.mano.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.global.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import java.io.IOException;

public class MemberAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;

    private final AuthenticationSuccessService authenticationSuccessService;

    public MemberAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessService authenticationSuccessService) {

        super("/login"); // 로그인 URL 설정
        setAuthenticationManager(authenticationManager);
        this.objectMapper = new ObjectMapper();
        this.authenticationSuccessService = authenticationSuccessService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        // JSON 요청에서 사용자 이름과 비밀번호 추출
        RequestGeneralMemberMainDto loginRequest = objectMapper.readValue(request.getInputStream(), RequestGeneralMemberMainDto.class);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        );

        return getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        authenticationSuccessService.handleSuccess(response, authResult);
//        createLoginResponse(response,ResponseMessage.LOGIN_SUCCESS);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        createLoginResponse(response,ResponseMessage.LOGIN_FAIL);

    }

    private void createLoginResponse(HttpServletResponse response, String responseMessage) throws IOException {

        // 리스폰스 객체에 에러 담기
        ApiResponse<?> apiResponse =  ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.LOGIN_SUCCESS);
        response.setStatus(HttpServletResponse.SC_OK); //
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        if(responseMessage.equals(ResponseMessage.LOGIN_FAIL)){
            apiResponse =  ApiResponse.response(HttpStatus.UNAUTHORIZED.value(), responseMessage);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //
        }

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // 직렬화하여 JSON 응답
        response.flushBuffer();  // flush는 특정 출력 스트림에 적용 / flushBuffer는 http 응답 전체에 적용, 응답의 상태 코드와 헤더를 포함

    }
}
