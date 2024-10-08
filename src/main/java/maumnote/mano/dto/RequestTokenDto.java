package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Schema(description = "accessToken 재발급 요청 DTO")
@Getter
public class RequestTokenDto {

    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZDY1NDVjOC03N2IzLTQxZDAtOWM2Yi04Nzc3ZjgzZjRjNzgiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MjgyMDMzNzMsImV4cCI6MTcyODgwODE3M30.Gds0mcNFlOOqCoiIpOFKmiiUyw1fDlHsYZNG5mKGZ-LrXSCyQkd08PU6hLiE3bhM3slOhnKqTB_JDxvOPx3JSg")
    @NotBlank(message = "refreshToken은 비어 있을 수 없습니다.")
    private String refreshToken;
}
