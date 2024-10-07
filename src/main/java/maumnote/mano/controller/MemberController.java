package maumnote.mano.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.ErrorResponse;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.dto.ResponseMemberJoinDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 api")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @Operation(summary = "회원가입", description = "이메일(email)과 비밀번호(password)를 입력하여 신규 회원 가입합니다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "회원 가입 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "회원 가입 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class, example = "{\"errorCode\": \"JOIN_FAIL\", \"errorMassage\":\"회원가입에 실패하였습니다. 다시 시도해 주세요.\"}")))
    })
    @PostMapping("/join")
    public ApiResponse<ResponseMemberJoinDto> joinGeneralMember(@Valid @RequestBody RequestGeneralMemberMainDto requestGeneralMemberMainDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.MEMBER_CREATE_SUCCESS, memberService.createGeneralMember(requestGeneralMemberMainDto));
    }
}
