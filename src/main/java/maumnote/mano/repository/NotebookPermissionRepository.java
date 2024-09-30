package maumnote.mano.repository;

import maumnote.mano.domain.NotebookPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotebookPermissionRepository extends JpaRepository<NotebookPermission, Long> {
    List<NotebookPermission> findByMemberId(String memberId);

    List<NotebookPermission> findAllByNotebookId(long notebookId);
}
