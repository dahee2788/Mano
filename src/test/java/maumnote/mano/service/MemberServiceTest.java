package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.MemberGeneralRepository;
import maumnote.mano.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberGeneralRepository memberGeneralRepository;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private ResponseEntity responseEntity;

    @Test
    @DisplayName("회원 저장 성공")
    void createMemberSuccess() {

        // given
        Member member = new Member();
        given(memberRepository.save(any(Member.class)))
                .willReturn(member);
        given(memberGeneralRepository.save(any(MemberGeneral.class)))
                .willReturn(MemberGeneral.builder()
                        .id(1)
                        .member(member)
                        .email("test@test.com")
                        .password("test")
                        .build());

        // when
        String result = memberService.createGeneralMember(new RequestGeneralMemberMainDto("test@test.com", "test"));

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("회원 저장 실패 - member")
    void createMemberFail1() {

        // given
        Member member = new Member();
        given(memberRepository.save(any(Member.class)))
                .willReturn(null);
        // when
        // then
        assertThrows(ManoCustomException.class, () -> {
            memberService.createGeneralMember(new RequestGeneralMemberMainDto("test@test.com", "test"));
        });
    }

    @Test
    @DisplayName("회원 저장 실패 - member_general")
    void createMemberFail2() {

        // given
        Member member = new Member();
        given(memberRepository.save(any(Member.class)))
                .willReturn(member);
        given(memberGeneralRepository.save(any(MemberGeneral.class)))
                .willReturn(null);

        // when
        // then
        assertThrows(ManoCustomException.class, () -> {
            memberService.createGeneralMember(new RequestGeneralMemberMainDto("test@test.com", "test"));
        });
    }
}
