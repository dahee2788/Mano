package maumnote.mano.controller;

import jakarta.validation.Valid;
import maumnote.mano.domain.Member;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public ApiResponse<Boolean> joinGeneralMember(@Valid @RequestBody RequestGeneralMemberMainDto requestGeneralMemberMainDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.MEMBER_CREATE_SUCCESS,memberService.createGeneralMember(requestGeneralMemberMainDto));
    }
}
