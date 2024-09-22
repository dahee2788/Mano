package maumnote.mano.repository;

import maumnote.mano.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRespository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String rolerName);
}
