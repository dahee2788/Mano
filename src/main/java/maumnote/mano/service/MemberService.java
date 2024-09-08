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

import javax.security.sasl.AuthenticationException;
import java.util.Optional;

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
        Member member = Member.newMember();

        if(!ObjectUtils.isEmpty(memberRepository.save(member))){
            MemberGeneral newMemberGeneral = memberGeneralRepository.save(MemberGeneral.fromDto(requestGeneralMemberMainDto, member));

            if(!ObjectUtils.isEmpty(newMemberGeneral)){
                result = member.getId();
            }
            else throw new ManoCustomException(ErrorCode.JOIN_FAIL);
        }
        else throw new ManoCustomException(ErrorCode.JOIN_FAIL);

        return result;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MemberGeneral> memberGeneral =  memberGeneralRepository.findByEmail(username);

        if (memberGeneral.isEmpty()) {
            throw new UsernameNotFoundException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage());
        }

      return memberGeneral.get();
    }

    public String authentication(RequestGeneralMemberMainDto requestGeneralMemberMainDto) throws AuthenticationException {

        MemberGeneral member =   this.memberGeneralRepository.findByEmail(requestGeneralMemberMainDto.getEmail())
                .orElseThrow(() -> new AuthenticationException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage()));

        if(!MemberGeneral.matchPassword(requestGeneralMemberMainDto.getPassword(), member.getPassword())){
            throw new AuthenticationException(ErrorCode.GENERAL_LOGIN_FAIL.getMessage());
        }

        return member.getMember().getId();
    }
}
