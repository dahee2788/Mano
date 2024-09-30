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
    @PreAuthorize("hasRole(T(maumnote.mano.global.security.Authority).USER)")
    ApiResponse<ResponseNotebookDto> createNotebook(@RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_CREATE_SUCCESS, notebookService.createNotebook(notebookDto));
    }

    @GetMapping("/notebook")
    @PreAuthorize("hasRole(T(maumnote.mano.global.security.Authority).USER)")
    ApiResponse<List<ResponseNotebookDto>> getNotebooks() {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, notebookService.findAllNotebook());
    }

    @PatchMapping("/notebook/{notebookId}")
    @PreAuthorize("hasRole(T(maumnote.mano.global.security.Authority).USER)")
    ApiResponse<ResponseNotebookDto> updateNotebook(@PathVariable("notebookId") long notebookId, @RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_UPDATE_SUCCESS, notebookService.updateNotebook(notebookId, notebookDto));
    }

    @DeleteMapping("/notebook/{notebookId}")
    @PreAuthorize("hasRole(T(maumnote.mano.global.security.Authority).USER)")
    ApiResponse<Boolean> deleteNotebook(@PathVariable("notebookId") long notebookId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_DELETE_SUCCESS, notebookService.deleteNotebook(notebookId));
    }


}
