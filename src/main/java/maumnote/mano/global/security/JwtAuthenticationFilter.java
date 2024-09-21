package maumnote.mano.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.global.util.PermitAllUrlLoader;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);
            String requestURI = request.getRequestURI();
            List<String> permitAllUrls = PermitAllUrlLoader.getPermitAllUrls();

            if (permitAllUrls.contains(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                Authentication authentication = this.tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                handleTokenResponse(response, ResponseMessage.INVALID_TOKEN);
                return;
            }
            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException e) {
            handleTokenResponse(response, ResponseMessage.EXPIRED_TOKEN);
        } catch (MalformedJwtException | IllegalArgumentException e) {
            handleTokenResponse(response, ResponseMessage.INVALID_TOKEN);
        }

    }

    private String resolveToken(HttpServletRequest request) {

        String token = request.getHeader(AUTHORIZATION_HEADER);
        if (!ObjectUtils.isEmpty(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(BEARER_PREFIX.length());
        }

        return null;
    }

    private void handleTokenResponse(HttpServletResponse response, String ResponseMessage) throws IOException {

        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        ApiResponse<?> apiResponse = ApiResponse.response(HttpStatus.UNAUTHORIZED.value(), ResponseMessage);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse)); // 직렬화하여 JSON 응답
        response.flushBuffer();
    }
}
