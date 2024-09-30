package maumnote.mano.repository;

import maumnote.mano.domain.NotebookPermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotebookPermissionRepository extends JpaRepository<NotebookPermission, Long> {
    List<NotebookPermission> findByMemberId(String memberId);

    List<NotebookPermission> findAllByNotebookId(long notebookId);
}
