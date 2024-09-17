package maumnote.mano.service;

import maumnote.mano.domain.Notebook;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.NotebookRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class NotebookService {
    private final NotebookRepository notebookRepository;

    public NotebookService(NotebookRepository notebookRepository) {
        this.notebookRepository = notebookRepository;
    }

    public ResponseNotebookDto create(RequestNotebookDto requestNotebookDto) {

        Notebook requestNotebook = Notebook.fromRequestDto(requestNotebookDto);
        Notebook saveNotebook = notebookRepository.save(requestNotebook);
        if (ObjectUtils.isEmpty(saveNotebook)) {
            throw new ManoCustomException(ErrorCode.NOTEBOOK_CREATE_FAIL);
        }

        return Notebook.toResponseDto(saveNotebook);
    }

    public List<ResponseNotebookDto> findAll() {

        List<Notebook> notebooks = notebookRepository.findAll();

        return notebooks.stream().map(Notebook::toResponseDto).toList();
    }

    public ResponseNotebookDto update(long id, RequestNotebookDto requestNotebookDto) {

        Notebook requestNotebook = Notebook.fromRequestDto(id, requestNotebookDto);
        Notebook saveNotebook = notebookRepository.save(requestNotebook);

        return Notebook.toResponseDto(saveNotebook);
    }

    public void delete(long id) {

        // 리턴형이 void인 메소드에 대해서 검증하는 방법..?
        notebookRepository.deleteById(id);
    }

}
