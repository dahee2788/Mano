package maumnote.mano.service;

import maumnote.mano.domain.Notebook;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.NotebookRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotebookServiceMockitoTest {

    @Mock
    private NotebookRepository notebookRepository;

    @InjectMocks
    private NotebookService notebookService;

    @Test
    @DisplayName("일기장 저장 성공")
    void successCreateNotebook() {

        // given
        given(notebookRepository.save(any(Notebook.class)))
                .willReturn(Notebook.builder()
                        .id(111L)
                        .name("즐거운일기장")
                        .build());
        // when
        ResponseNotebookDto newNotebook =  notebookService.create(new RequestNotebookDto("즐거운일기장"));

        // then
        Assertions.assertNotNull(newNotebook);
        Assertions.assertEquals(111L, newNotebook.getId());
        Assertions.assertEquals("즐거운일기장",newNotebook.getName());
    }
    @Test
    @DisplayName("일기장 저장 실패")
    void failCreateNotebook() {

        // given
        given(notebookRepository.save(any(Notebook.class)))
                .willReturn(null);
        // when
        ManoCustomException exception =  assertThrows(ManoCustomException.class,
                ()->notebookService.create(new RequestNotebookDto("즐거운일기장")));
        // then
        Assertions.assertEquals(ErrorCode.NOTEBOOK_CREATE_FAIL,exception.getErrorCode());
    }

    @Test
    @DisplayName("일기장 목록 조회")
    void getNotebooks() {

        // given
        // Notebook 객체 생성
        Notebook notebook1 = new Notebook(1L, "일기장1");
        Notebook notebook2 = new Notebook(2L, "일기장2");
        List<Notebook> notebooks = List.of(notebook1,notebook2);
        given(notebookRepository.findAll())
                .willReturn(notebooks);
        // when
        List<ResponseNotebookDto> responseNotebooks =  notebookService.findAll();

        // then
        Assertions.assertEquals(2,responseNotebooks.size());
    }

    @Test
    @DisplayName("일기장 수정")
    void updateNotebook() {

        // given
        given(notebookRepository.save(any(Notebook.class)))
                .willReturn(Notebook.builder()
                        .id(111L)
                        .name("즐거운일기장_수정")
                        .build());
        // when
        ResponseNotebookDto newNotebook =  notebookService.update(111L, new RequestNotebookDto("즐거운일기장_수정"));

        // then
        Assertions.assertNotNull(newNotebook);
        Assertions.assertEquals(111L, newNotebook.getId());
        Assertions.assertEquals("즐거운일기장_수정",newNotebook.getName());
    }

    @Test
    @DisplayName("일기장 삭제")
    void deleteNotebook() {

        // 보통 delete의 테스트 코드는 어떻게 짜는지요..?
        //  저장->삭제->조회 해서 없는지 체크하는걸까요?

        // given
        long notebookId = 111L;

        // when
        notebookService.delete(notebookId);

        // then
        verify(notebookRepository, times(1)).deleteById(notebookId);


    }
}