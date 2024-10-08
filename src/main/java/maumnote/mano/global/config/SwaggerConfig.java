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

import java.io.IOException;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() throws IOException {

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
                                .name("로그인/로그아웃")
                                .description("로그인/로그아웃 api")
                ))
                .path("/login", new PathItem()
                        .post(new Operation()
                                .tags(List.of(
                                        "로그인/로그아웃"
                                ))
                                .summary("로그인")
                                .description("가입한 이메일과 비밀번호를 입력하여 로그인 합니다.")
                                .operationId("login")
                                .requestBody(new RequestBody()
                                        .required(true)
                                        .content(new Content()
                                                .addMediaType("application/json", new MediaType()
                                                        .schema(new Schema()
                                                                .required(List.of("email", "password"))
                                                                .addProperty("email", new Schema()
                                                                        .type("string")
                                                                        .description("이메일")
                                                                        .example("test@test.com"))
                                                                .addProperty("password", new Schema()
                                                                        .type("string")
                                                                        .description("비밀번호")
                                                                        .example("test")
                                                                )
                                                        )
                                                )
                                        )
                                )
                                .responses(new ApiResponses()
                                        .addApiResponse("200", new ApiResponse()
                                                .description("OK")
                                                .content(new Content().addMediaType("application/json", new MediaType()
                                                        .schema(new Schema()
                                                                .type("object")
                                                                .addProperty("statusCode", new Schema()
                                                                        .type("integer")
                                                                        .example(200))
                                                                .addProperty("responseMsg", new Schema()
                                                                        .type("string")
                                                                        .example("로그인 성공"))
                                                                .addProperty("data", new Schema()
                                                                        .type("object") // data는 객체 타입
                                                                        .addProperty("accessToken", new Schema()
                                                                                .type("string")
                                                                                .example("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZDY1NDVjOC03N2IzLTQxZDAtOWM2Yi04Nzc3ZjgzZjRjNzgiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MjgzODQzMjEsImV4cCI6MTcyODM4NjEyMX0.39jBbyWQziq3FadXJ1zvJixAAHiZJFuspld7oG1R-j_xoPDAyQJltpRsTiK3GRpyloqUOhoYl0NIk1GNoIxGHg"))
                                                                        .addProperty("refreshToken", new Schema()
                                                                                .type("string")
                                                                                .example("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZDY1NDVjOC03N2IzLTQxZDAtOWM2Yi04Nzc3ZjgzZjRjNzgiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MjgzODQzMjEsImV4cCI6MTcyODk4OTEyMX0.4vN5EuIA1NXHgBowNUoBHInITfC0TGuPGqSzKZkTcFjGAD5oheIL0ixdVqzTeKXhj8iEmbqyG2BZWBKplcX_SQ"))
                                                                ))))
                                        )
                                        .addApiResponse("401", new ApiResponse().description("로그인 실패")
                                                .content(new Content()
                                                        .addMediaType("application/json", new MediaType()
                                                                .schema(new Schema()
                                                                        .type("object")
                                                                        .addProperty("statusCode", new Schema()
                                                                                .type("integer")
                                                                                .example(401))
                                                                        .addProperty("responseMsg", new Schema()
                                                                                .type("string")
                                                                                .example("로그인 실패"))
                                                                        .addProperty("data", new Schema()
                                                                                .type("object")
                                                                                .example(null)
                                                                        )
                                                                )
                                                        )
                                                ))
                                )
                        )
                )
                .info(apiInfo());
    }

    private Info apiInfo() throws IOException {
        return new Info()
                .title("MaumNote : Mano")
                .description("하루의 기분을 작성할 수 있는 일기 Api 입니다.\n\n header에 인증 정보가 필요 합니다.\n로그인 하여 응답받은 accessToken을 'Authorization: Bearer {accessToken}' 방식으로 요청 header에 추가 해주세요. \n\n 스웨거 문서에서 바로 테스트를 해보시려면, Authorize 버튼을 눌러서 'Bearer {accessToken}' 을 입력해주시면 됩니다.")
                .version("1.0");
    }


}
