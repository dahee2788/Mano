package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.dto.RequestGeneralMemberMainDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.MemberGeneralRepository;
import maumnote.mano.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberGeneralRepository memberGeneralRepository;

    public MemberService(MemberRepository memberRepository, MemberGeneralRepository memberGeneralRepository) {

        this.memberRepository = memberRepository;
        this.memberGeneralRepository = memberGeneralRepository;
    }

    @Transactional
    public boolean createGeneralMember(RequestGeneralMemberMainDto requestGeneralMemberMainDto) {

        boolean result = false;
        Member member = Member.newMember();

        if(!ObjectUtils.isEmpty(memberRepository.save(member))){
            MemberGeneral newMemberGeneral = memberGeneralRepository.save(MemberGeneral.fromDto(requestGeneralMemberMainDto, member));

            if(!ObjectUtils.isEmpty(newMemberGeneral)){
                result = true;
            }
            else throw new ManoCustomException(ErrorCode.JOIN_FAIL);
        }
        else throw new ManoCustomException(ErrorCode.JOIN_FAIL);

        return result;
    }
}
