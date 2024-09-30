package maumnote.mano.repository;

import maumnote.mano.domain.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook, Long> {
    List<Notebook> findAllByIdOrderedByCreateDate(List<Long> notebookIds);
}
