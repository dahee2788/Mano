package maumnote.mano.global.security;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class SecretKeyProvider {
    private static final String SECRET_KEY_STRING = "dahee-new-project-maumnote-jwt-secret-key-abcdefghijklmnopqrstuvwxyz"; // 비밀 키 문자열
    private static final SecretKey SECRET_KEY;

    static { // 클래스가 로드될 때 한번 실행
        byte[] apiKeySecretBytes = SECRET_KEY_STRING.getBytes();
        SECRET_KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }
}
