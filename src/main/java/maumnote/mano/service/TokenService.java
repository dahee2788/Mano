package maumnote.mano.service;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Member;
import maumnote.mano.dto.RequestTokenDto;
import maumnote.mano.dto.ResponseTokenDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.global.security.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;

    public ResponseTokenDto reGenerateAccessToken(RequestTokenDto requestTokenDto) {

        String refreshToken = requestTokenDto.getRefreshToken();
        // 토큰 체크
        if (tokenProvider.validateToken(refreshToken)) {
            // 다시 발급
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            Object principal = authentication.getPrincipal();
            String memberId = ((Member) principal).getId();
            return tokenProvider.reGenerateTokenResponse(memberId, authentication.getAuthorities(), refreshToken);

        } else {
            throw new ExpiredJwtException(null, null, ResponseMessage.EXPIRED_TOKEN);
        }
    }
}
