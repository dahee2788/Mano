package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.Role;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.dto.ResponseMemberJoinDto;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.MemberRepository;
import maumnote.mano.repository.RoleRespository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static maumnote.mano.global.Constants.DEFAULT_ROLE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private RoleRespository roleRespository;

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원 저장 성공")
    void createMemberSuccess() {

        // given
        Member member = new Member();
        given(memberRepository.save(any(Member.class)))
                .willReturn(member);
        given(roleRespository.findByRoleName(DEFAULT_ROLE))
                .willReturn(Optional.of(Role.builder()
                        .id(1L)
                        .roleName("ROLE_USER")
                        .build()));

        // when
        ResponseMemberJoinDto result = memberService.createGeneralMember(new RequestGeneralMemberMainDto("test@test.com", "test"));

        // then
        assertNotNull(result);
    }

    @Test
    @DisplayName("회원 저장 실패 - member")
    void createMemberFail1() {

        // given
        given(roleRespository.findByRoleName(DEFAULT_ROLE))
                .willReturn(Optional.of(Role.builder()
                        .id(1L)
                        .roleName("ROLE_USER")
                        .build()));
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
    @DisplayName("로그인 정보에 해당하는 회원정보 가져오기")
    void loadUserByUsername() {

        // given
        given(memberRepository.findByEmail(any()))
                .willReturn(Optional.of(Member.builder()
                        .id("cd6545c8-77b3-41d0-9c6b-8777f83f4c78")
                        .email("test@test.com")
                        .password("$2a$10$V0qqknCUlOZMjmWyf7YZM.EuMxoCiRGYuJpJQMsLJQwXTB1BINhci")
                        .build()));
        // when
        UserDetails userDetails = memberService.loadUserByUsername(any());

        // then
        assertEquals("test@test.com", userDetails.getUsername());
        assertEquals("cd6545c8-77b3-41d0-9c6b-8777f83f4c78", ((Member) userDetails).getId());
    }


}
