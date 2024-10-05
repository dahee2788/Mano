package maumnote.mano.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.global.anotation.HasUserRole;
import maumnote.mano.service.NoteService;
import maumnote.mano.service.NotebookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;
    private final NoteService noteService;

    @PostMapping("/notebook")
    @HasUserRole
    ApiResponse<ResponseNotebookDto> createNotebook(@RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_CREATE_SUCCESS, notebookService.createNotebook(notebookDto));
    }

    @GetMapping("/notebook")
    @HasUserRole
    ApiResponse<List<ResponseNotebookDto>> getNotebooks() {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, notebookService.findAllNotebook());
    }

    @GetMapping("/notebook/{notebookId}")
    @HasUserRole
    ApiResponse<List<ResponseNoteDto>> getNotesByNotebookId(@PathVariable("notebookId") long notebookId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, noteService.findAllNoteByNotebookId(notebookId));
    }

    @PatchMapping("/notebook/{notebookId}")
    @HasUserRole
    ApiResponse<ResponseNotebookDto> updateNotebook(@PathVariable("notebookId") long notebookId, @RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_UPDATE_SUCCESS, notebookService.updateNotebook(notebookId, notebookDto));
    }

    @DeleteMapping("/notebook/{notebookId}")
    @HasUserRole
    ApiResponse<Boolean> deleteNotebook(@PathVariable("notebookId") long notebookId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_DELETE_SUCCESS, notebookService.deleteNotebook(notebookId));
    }


}
