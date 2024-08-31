package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.dto.RequestCreateGeneralMemberDto;
import maumnote.mano.repository.MemberGeneralRepository;
import maumnote.mano.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    @Test
    @DisplayName("회원 저장 성공")
    void createMember() {

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
        boolean result = memberService.createGeneralMember(new RequestCreateGeneralMemberDto("test@test.com","test"));

        // then
        assertTrue(result);
    }

}