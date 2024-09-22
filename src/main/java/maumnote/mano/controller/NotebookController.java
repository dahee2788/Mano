package maumnote.mano.controller;

import jakarta.validation.Valid;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.service.NotebookService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @PostMapping("/notebook")
    @PreAuthorize("hasRole('USER')")
    ApiResponse<ResponseNotebookDto> createNotebook(@RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_CREATE_SUCCESS, notebookService.create(notebookDto));
    }

    @GetMapping("/notebook")
    @PreAuthorize("hasRole('USER')")
    ApiResponse<List<ResponseNotebookDto>> getNotebooks() {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, notebookService.findAll());
    }

    @PatchMapping("/notebook/{notebookId}")
    @PreAuthorize("hasRole('USER')")
    ApiResponse<ResponseNotebookDto> updateNotebook(@PathVariable("notebookId") long notebookId, @RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_UPDATE_SUCCESS, notebookService.update(notebookId, notebookDto));
    }

    @DeleteMapping("/notebook/{notebookId}")
    @PreAuthorize("hasRole('USER')")
    ApiResponse<?> deleteNotebook(@PathVariable("notebookId") long notebookId) {

        notebookService.delete(notebookId);
        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_DELETE_SUCCESS);
    }


}
