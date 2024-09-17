package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.MemberGeneralRepository;
import maumnote.mano.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberGeneralRepository memberGeneralRepository;

    public MemberService(MemberRepository memberRepository, MemberGeneralRepository memberGeneralRepository) {

        this.memberRepository = memberRepository;
        this.memberGeneralRepository = memberGeneralRepository;
    }

    @Transactional
    public String createGeneralMember(RequestGeneralMemberMainDto requestGeneralMemberMainDto) {

        String result;
        Member member = Member.createNewMember();

        if (!ObjectUtils.isEmpty(memberRepository.save(member))) {
            MemberGeneral newMemberGeneral = memberGeneralRepository.save(MemberGeneral.fromDto(requestGeneralMemberMainDto, member));

            if (!ObjectUtils.isEmpty(newMemberGeneral)) {
                result = member.getId();
            } else {
                throw new ManoCustomException(ErrorCode.JOIN_FAIL);
            }
        } else {
            throw new ManoCustomException(ErrorCode.JOIN_FAIL);
        }

        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return memberGeneralRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));

    }

    public UserDetails loadUserById(String id) {
        return memberRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));
    }
}
