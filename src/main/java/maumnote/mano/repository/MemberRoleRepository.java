package maumnote.mano.repository;

import maumnote.mano.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {

    List<MemberRole> findAllByMemberId(String member_id);
}
