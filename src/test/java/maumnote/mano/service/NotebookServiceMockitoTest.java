package maumnote.mano.service;

import maumnote.mano.domain.Notebook;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.repository.NotebookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotebookServiceMockitoTest {

    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NotebookService notebookService;

    @Test
    @DisplayName("일기장 조회 성공")
    void saveMock() {
        // given
        given(notebookRepository.save(any(Notebook.class)))
                .willReturn(Notebook.builder()
                        .id(111L)
                        .name("즐거운일기장")
                        .build());
        // when
        ResponseNotebookDto newNotebook =  notebookService.save(new RequestNotebookDto("즐거운일기장"));

        // then
        Assertions.assertNotNull(newNotebook);
        Assertions.assertEquals(111L, newNotebook.getId());
        Assertions.assertEquals("즐거운일기장",newNotebook.getName());
    }
}