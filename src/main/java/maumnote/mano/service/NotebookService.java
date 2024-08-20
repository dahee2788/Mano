package maumnote.mano.service;

import maumnote.mano.domain.Notebook;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import maumnote.mano.repository.NotebookRepository;

@Service
public class NotebookService {

    @Autowired
    private NotebookRepository notebookRepository;

    /* 일기장 저장 */
    public ResponseNotebookDto save(RequestNotebookDto requestNotebookDto) {
        ResponseNotebookDto notebook;
        try {
            Notebook requestNotebook = Notebook.builder()
                    .name(requestNotebookDto.getName())
                    .createId("system")
                    .updateId("system")
                    .build();
            Notebook resNotebook = notebookRepository.save(requestNotebook);
            notebook = ResponseNotebookDto.builder()
                    .id(resNotebook.getId())
                    .name(resNotebook.getName())
                    .createDate(resNotebook.getCreateDate())
                    .createId(resNotebook.getCreateId())
                    .updateDate(resNotebook.getUpdateDate())
                    .updateId(resNotebook.getUpdateId())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return notebook;
    }

}
