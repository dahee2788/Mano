package maumnote.mano.repository;

import maumnote.mano.domain.Notebook;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class NotebookRepositoryTest {
    
    @Autowired
    NotebookRepository notebookRepository;

    @Test
    void saveNotebook() {
        // given
        Notebook notebook = Notebook.builder()
                .name("test일기장")
                .createId("test")
                .updateId("test")
                .build();
        // when
        Notebook newNotebook = notebookRepository.save(notebook);
        // then
        System.out.println(newNotebook.toString());
        assertEquals(notebook.getName(), newNotebook.getName());
    }

}