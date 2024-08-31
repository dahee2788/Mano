package maumnote.mano.repository;

import maumnote.mano.domain.MemberGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberGeneralRepository extends JpaRepository<MemberGeneral, Long> {


}
