package maumnote.mano.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestTokenDto;
import maumnote.mano.dto.ResponseTokenDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/refresh-token")
    public ApiResponse<ResponseTokenDto> refreshToken(@Valid @RequestBody RequestTokenDto requestTokenDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, tokenService.reGenerateAccessToken(requestTokenDto));
    }
}
