package maumnote.mano.service;

import maumnote.mano.domain.Notebook;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import org.springframework.stereotype.Service;
import maumnote.mano.repository.NotebookRepository;
import org.springframework.util.ObjectUtils;

@Service
public class NotebookService {
    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    /* 일기장 저장 */
    public ResponseNotebookDto save(RequestNotebookDto requestNotebookDto){

            Notebook requestNotebook = Notebook.fromRequestDto(requestNotebookDto);
            Notebook resNotebook = notebookRepository.save(requestNotebook);
            if(ObjectUtils.isEmpty(resNotebook)) {
                throw new ManoCustomException(ErrorCode.NOTEBOOK_CREATE_FAIL);
            }

        return Notebook.toResponseDto(resNotebook);
    }

}
