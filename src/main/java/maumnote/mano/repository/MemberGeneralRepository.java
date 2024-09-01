package maumnote.mano.repository;

import maumnote.mano.domain.MemberGeneral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberGeneralRepository extends JpaRepository<MemberGeneral, Long> {

    Optional<MemberGeneral> findByEmailAndPassword(String email, String password);
    Optional<MemberGeneral> findByEmail(String email);

}
