package maumnote.mano.controller;

import jakarta.validation.Valid;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.global.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import maumnote.mano.service.NotebookService;

import java.util.List;


@RestController
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }

    @PostMapping("/notebook")
    ApiResponse<ResponseNotebookDto> createNotebook(@RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_CREATE_SUCCESS, notebookService.create(notebookDto));
    }

    @GetMapping("/notebook")
    ApiResponse<List<ResponseNotebookDto>> getNotebooks () {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, notebookService.findAll());
    }

    @PatchMapping("/notebook/{notebookId}")
    ApiResponse<ResponseNotebookDto> updateNotebook (@PathVariable("notebookId") long notebookId, @RequestBody @Valid RequestNotebookDto notebookDto) throws Exception{

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_UPDATE_SUCCESS, notebookService.update(notebookId,notebookDto));
    }

    @DeleteMapping("/notebook/{notebookId}")
    ApiResponse<?> deleteNotebook (@PathVariable("notebookId") long notebookId) {

        notebookService.delete(notebookId);
        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_DELETE_SUCCESS);
    }








}
