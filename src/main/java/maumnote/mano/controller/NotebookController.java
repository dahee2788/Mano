package maumnote.mano.controller;

import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import maumnote.mano.service.NotebookService;

@RestController
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }
    @PostMapping("/notebook")
    ResponseEntity<ResponseNotebookDto> saveNotebook(RequestNotebookDto notebookDto) {
        ResponseNotebookDto result = null;
        if(ObjectUtils.isEmpty(notebookDto) || ObjectUtils.isEmpty(notebookDto.getName())) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(result);
        }
        result = notebookService.save(notebookDto);
        if(ObjectUtils.isEmpty(result)) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(result);
        }
        return ResponseEntity.ok(result);
    }
}
