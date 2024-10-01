package maumnote.mano.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestNoteDto;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @PostMapping("/note")
    ApiResponse<ResponseNoteDto> createNote(@RequestBody @Valid RequestNoteDto requestNoteDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTE_CREATE_SUCCESS, noteService.createNote(requestNoteDto));
    }

    @PatchMapping("/note/{noteId}")
    ApiResponse<ResponseNoteDto> updateNote(@PathVariable("noteId") long noteId, @RequestBody @Valid RequestNoteDto requestNoteDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTE_UPDATE_SUCCESS, noteService.updateNote(noteId, requestNoteDto));
    }

    @GetMapping("/note/{noteId}")
    ApiResponse<ResponseNoteDto> getNote(@PathVariable("noteId") long noteId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, noteService.findNote(noteId));
    }

    @DeleteMapping("/note/{noteId}")
    ApiResponse<Boolean> deleteNote(@PathVariable("noteId") long noteId) {
        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, noteService.deleteNote(noteId));
    }


}
