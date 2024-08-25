package maumnote.mano.service;

import maumnote.mano.domain.Notebook;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import org.springframework.stereotype.Service;
import maumnote.mano.repository.NotebookRepository;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotebookService {
    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    /* 일기장 생성 */
    public ResponseNotebookDto create(RequestNotebookDto requestNotebookDto){

            Notebook requestNotebook = Notebook.fromRequestDto(requestNotebookDto);
            Notebook saveNotebook = notebookRepository.save(requestNotebook);
            if(ObjectUtils.isEmpty(saveNotebook)) {
                throw new ManoCustomException(ErrorCode.NOTEBOOK_CREATE_FAIL);
            }

        return Notebook.toResponseDto(saveNotebook);
    }

    /* 일기장 조회 */
    public List<ResponseNotebookDto> findAll(){

        List<Notebook> notebooks =  notebookRepository.findAll();

        return notebooks.stream().map(Notebook::toResponseDto).toList();
    }

    /* 일기장 수정 */
    public ResponseNotebookDto update(long id, RequestNotebookDto requestNotebookDto){

        Notebook requestNotebook = Notebook.fromRequestDto(id, requestNotebookDto);
        Notebook saveNotebook = notebookRepository.save(requestNotebook);

        return Notebook.toResponseDto(saveNotebook);
    }

    /* 일기장 삭제 */
    public void delete(long id){
        notebookRepository.deleteById(id);
    }

}
