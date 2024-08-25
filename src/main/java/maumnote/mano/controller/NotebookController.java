package maumnote.mano.controller;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.global.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import maumnote.mano.service.NotebookService;

import javax.validation.Valid;


@RestController
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService)
    {
        this.notebookService = notebookService;
    }

    @PostMapping("/notebook")
    ApiResponse<ResponseNotebookDto> saveNotebook(@Valid @RequestBody RequestNotebookDto notebookDto)  {
        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_CREATE_SUCCESS, notebookService.save(notebookDto));
    }
}
