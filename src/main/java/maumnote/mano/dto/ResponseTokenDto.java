package maumnote.mano.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "토큰 응답 DTO")
@Getter
@Builder
public class ResponseTokenDto {

    @Schema(description = "엑세스 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZDY1NDVjOC03N2IzLTQxZDAtOWM2Yi04Nzc3ZjgzZjRjNzgiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MjgyMDMzNzMsImV4cCI6MTcyODIwNTE3M30.zPUpDCqlq7OBpS8QkCeXCOQyC3xV4403yJavbx1PtcK5zWpX4kApAqA_O-kJGypXkPx6EyYC5zYH3b0KGLBwnQ")
    private String accessToken;
    @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjZDY1NDVjOC03N2IzLTQxZDAtOWM2Yi04Nzc3ZjgzZjRjNzgiLCJyb2xlcyI6W3siYXV0aG9yaXR5IjoiUk9MRV9VU0VSIn1dLCJpYXQiOjE3MjgyMDMzNzMsImV4cCI6MTcyODgwODE3M30.Gds0mcNFlOOqCoiIpOFKmiiUyw1fDlHsYZNG5mKGZ-LrXSCyQkd08PU6hLiE3bhM3slOhnKqTB_JDxvOPx3JSg")
    private String refreshToken;
}
