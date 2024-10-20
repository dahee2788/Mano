package maumnote.mano.service;

import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Member;
import maumnote.mano.domain.Role;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.dto.ResponseMemberJoinDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.MemberRepository;
import maumnote.mano.repository.RoleRespository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import static maumnote.mano.global.Constants.DEFAULT_ROLE;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final RoleRespository roleRespository;


    @Transactional
    public ResponseMemberJoinDto createGeneralMember(RequestGeneralMemberMainDto requestGeneralMemberMainDto) {

        if (memberRepository.findByEmail(requestGeneralMemberMainDto.getEmail()).isPresent()) {
            throw new ManoCustomException(ErrorCode.DUPLICATE_MEMBER_EMAIL);
        }

        Role defaultRole = roleRespository.findByRoleName(DEFAULT_ROLE).orElseThrow();
        Member member = Member.getGeneralVoFromDto(requestGeneralMemberMainDto, defaultRole);
        Member saved = memberRepository.save(member);
        if (!ObjectUtils.isEmpty(saved)) {
            return Member.toResponseDto(saved);
        } else {
            throw new ManoCustomException(ErrorCode.JOIN_FAIL);
        }
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return memberRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));

    }

    @Transactional
    public UserDetails loadUserById(String id) {
        return memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));
    }
}
