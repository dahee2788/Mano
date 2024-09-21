package maumnote.mano.service;

import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Member;
import maumnote.mano.domain.MemberRole;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.dto.ResponseMemberJoinDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.MemberRepository;
import maumnote.mano.repository.MemberRoleRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public ResponseMemberJoinDto createGeneralMember(RequestGeneralMemberMainDto requestGeneralMemberMainDto) {

        Member member = Member.getGeneralVoFromDto(requestGeneralMemberMainDto);
        Member saved = memberRepository.save(member);
        if (!ObjectUtils.isEmpty(saved)) {
            return Member.toResponseDto(saved);
        } else {
            throw new ManoCustomException(ErrorCode.JOIN_FAIL);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));

    }

    public UserDetails loadUserById(String id) {
        return memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));
    }

    public List<MemberRole> getMemberRoles(String memberId) {
        return memberRoleRepository.findAllByMemberId(memberId);
    }
}
