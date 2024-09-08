package maumnote.mano.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRATION_TIME = 1 * 60 * 60 * 1000; // 1시간
    private static final String KEY_ROLES = "roles";
    @Value("${spring.jwt.secret}")
    private String secretkey;

    /**
     * 토큰 생성(발급)
     * @param memberId
     * @param roles
     * @return
     */

    public String generateToken(String memberId, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put(KEY_ROLES, roles);

        Date now = new Date();
        Date expiresDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

       return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 생성시간
                .setExpiration(expiresDate) // 만료시간
                .signWith(SignatureAlgorithm.HS512, this.secretkey) // 사용할 암호화 알고리즘, 비밀키
                .compact();
    }

    /**
     * jwt토큰으로 부터 회원 키값 가져오기
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        return parseClaims(token).getSubject();
    }

    /**
     * 토큰 만료 확인
     * @param token
     * @return
     */
    public boolean validateToken(String token) {
        if(ObjectUtils.isEmpty(token)) return false;
        Claims claims = parseClaims(token);
        return !claims.getExpiration().before(new Date()); // 만료시간보다 지금이 이후인지
    }

    /**
     * 토큰에서 claim 부분 가져오기
     * @param token
     * @return
     */
    private Claims parseClaims(String token) {
        return Jwts.parser().setSigningKey(this.secretkey).parseClaimsJws(token).getBody();
    }
}
