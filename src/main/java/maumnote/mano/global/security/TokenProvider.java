package maumnote.mano.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.ResponseTokenDto;
import maumnote.mano.service.MemberService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 7 * 24 * 60 * 60 * 1000;
    private static final String KEY_ROLES = "roles";

    private final MemberService memberService;


    public ResponseTokenDto getTokenResponse(String memberId, Collection<? extends GrantedAuthority> roles) {

        String accessToken = generateToken(memberId, roles, ACCESS_TOKEN_EXPIRATION_TIME);
        String refreshToken = generateToken(memberId, roles, REFRESH_TOKEN_EXPIRATION_TIME);

        return ResponseTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public ResponseTokenDto reGenerateTokenResponse(String memberId, Collection<? extends GrantedAuthority> roles, String refreshToken) {

        String accessToken = generateToken(memberId, roles, ACCESS_TOKEN_EXPIRATION_TIME);

        return ResponseTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateToken(String memberId, Collection<? extends GrantedAuthority> roles, long expirationTime) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put(KEY_ROLES, roles);

        Date now = new Date();
        Date expiresDate = new Date(now.getTime() + expirationTime);

        return Jwts.builder().setClaims(claims).setIssuedAt(now) // 생성시간
                .setExpiration(expiresDate) // 만료시간
                .signWith(SecretKeyProvider.getSecretKey(), SignatureAlgorithm.HS512) // 사용할 암호화 알고리즘, 비밀키
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = memberService.loadUserById(getUsernameFromToken(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean validateToken(String token) {
        if (ObjectUtils.isEmpty(token)) return false;
        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date()); // 만료시간보다 지금이 이후인지
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(SecretKeyProvider.getSecretKey()).build().parseClaimsJws(token).getBody();
    }
}
