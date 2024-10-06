package maumnote.mano.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        SecurityScheme apiKey = new SecurityScheme()
                .type(SecurityScheme.Type.APIKEY)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer")
                .name("Authorization");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Token");

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth")) // API에서 인증 요구
                .components(new Components().addSecuritySchemes("Bearer Token", apiKey))
                .addSecurityItem(securityRequirement)
                .tags(List.of(
                        new Tag()
                                .name("Authentication")
                                .description("Login/logout controller")
                ))
//                .path("/logout", new PathItem()
//                        .post(new Operation()
//                                .tags(List.of(
//                                        "Authentication"
//                                ))
//                                .summary("Logout")
//                                .description("Logout the current user.")
//                                .operationId("logout")
//                                .responses(new ApiResponses()
//                                        .addApiResponse("200", new ApiResponse().description("OK"))
//                                )
//                        )
//                )
                .path("/login", new PathItem()
                        .post(new Operation()
                                .tags(List.of(
                                        "Authentication"
                                ))
                                .summary("Login")
                                .description("응답받은 access token을 'Bearer + token' 방식으로 Authorize에 입력해주세요.")
                                .operationId("login")
                                .requestBody(new RequestBody()
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema()
                                                                .addProperty("email", new Schema().type("string"))
                                                                .addProperty("password", new Schema().type("string"))
                                                        )
                                                )
                                        )
                                )
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse().description("OK"))
                                        .addApiResponse("401", new ApiResponse().description("이메일과 비밀번호를 확인해주세요."))
                                )
                        )
                )
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("MaumNote : Mano")
                .description("하루의 기분을 작성할 수 있는 일기 Api 입니다.\n\n 로그인부터 하시어 응답받은 token으로 인증을 먼저 진행해주세요.")
                .version("1.0");
    }


}
