package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.MemberGeneral;
import maumnote.mano.dto.RequestCreateGeneralMemberDto;
import maumnote.mano.repository.MemberGeneralRepository;
import maumnote.mano.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberGeneralRepository memberGeneralRepository;

    public MemberService(MemberRepository memberRepository, MemberGeneralRepository memberGeneralRepository) {
        this.memberRepository = memberRepository;
        this.memberGeneralRepository = memberGeneralRepository;
    }

    @Transactional
    public boolean createGeneralMember(RequestCreateGeneralMemberDto requestCreateGeneralMemberDto) {

        boolean result = false;
        Member member = Member.newMember();

        if(!ObjectUtils.isEmpty(memberRepository.save(member))){
            MemberGeneral newMemberGeneral = memberGeneralRepository.save(MemberGeneral.fromDto(requestCreateGeneralMemberDto, member));
            if(!ObjectUtils.isEmpty(newMemberGeneral)) result = true;
        }

        return result;
    }
}
