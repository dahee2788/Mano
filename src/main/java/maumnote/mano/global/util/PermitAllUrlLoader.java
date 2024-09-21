package maumnote.mano.global.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PermitAllUrlLoader {

    @Getter
    private static final List<String> permitAllUrls;

    static {
        permitAllUrls = loadPermitAllUrls();
    }

    private static List<String> loadPermitAllUrls() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            InputStream inputStream = PermitAllUrlLoader.class.getResourceAsStream("/json/permit-all-urls.json");
            PermitAllUrlsConfig config = objectMapper.readValue(inputStream, PermitAllUrlsConfig.class);
            return config.getPermitAllUrls();
        } catch (IOException e) {
            throw new ManoCustomException(ErrorCode.INTERNAL_PROCESSING_ERROR, e);
        }
    }

    // 내부 클래스로 JSON 구조를 매핑하기 위한 POJO
    @Getter
    private static class PermitAllUrlsConfig {
        private List<String> permitAllUrls;
    }
}