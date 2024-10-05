package maumnote.mano.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // API에서 인증 요구
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth",  // "bearerAuth"라는 인증 스키마 정의
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)  // HTTP 방식 인증
                                        .scheme("bearer")                // Bearer 토큰 방식
                                        .bearerFormat("JWT")))
                .info(apiInfo());
    }

    // todo : /login endpoint 노출안되고 있음. 
    @Bean
    public OpenApiCustomiser loginEndpointCustomizer() {
        return openApi -> {
            // /login 엔드포인트에 POST 요청을 정의
            Operation loginOperation = new Operation()
                    .summary("User Login")  // 요약 정보
                    .description("API to login users");  // 상세 설명

            // PathItem에 POST 메소드와 위에서 정의한 operation 추가
            PathItem loginPathItem = new PathItem()
                    .post(loginOperation);

            // openApi 문서의 paths에 /login 엔드포인트 추가
            openApi.getPaths().addPathItem("/login", loginPathItem);
        };
    }

    private Info apiInfo() {
        return new Info()
                .title("MaumNote : Mano")
                .description("하루의 기분을 작성할 수 있는 일기 Api 입니다.")
                .version("2.0");
    }


}
