package maumnote.mano.global;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

     // 에러가 발생했을때 호출되는 메서드
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 리퀘스트 객체에 에러 담기

        // 인증 실패하면 리다이렉트 처리를 통해 다른 곳으로 보내는게 아니라 서버내 다른 곳으로 보내서 처리
        setUseForward(true);
        // 인증실패 사용자가 리다이렉트될 기본 url
        setDefaultFailureUrl("/login?error");
        // Request 로그인 실패 메세지를 담아서 다른 서비스로 처리 요청
        request.setAttribute("errorMessage","로그인에 실패하였습니다.");
        super.onAuthenticationFailure(request, response, exception);
    }
}
