package maumnote.mano.global.security;

import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class SecretKeyProvider {
    private static SecretKey SECRET_KEY;

    @Value("${jwt.secret.key}")
    private String secretKeyString;

    public static SecretKey getSecretKey() {
        return SECRET_KEY;
    }

    @PostConstruct
    public void init() {
        byte[] apiKeySecretBytes = secretKeyString.getBytes();
        SECRET_KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }
}
