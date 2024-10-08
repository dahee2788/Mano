package maumnote.mano.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.ErrorResponse;
import maumnote.mano.dto.RequestNoteDto;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.global.anotation.HasUserRole;
import maumnote.mano.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "일기", description = "일기 관리 api")
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "일기 생성", description = "일기 내용 객체를 전송하여 일기를 생성한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/note")
    @HasUserRole
    ApiResponse<ResponseNoteDto> createNote(@RequestBody @Valid RequestNoteDto requestNoteDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTE_CREATE_SUCCESS, noteService.createNote(requestNoteDto));
    }

    @Operation(summary = "일기 수정", description = "일기 내용 객체를 전송하여 일기를 변경한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/note/{noteId}")
    @HasUserRole
    ApiResponse<ResponseNoteDto> updateNote(
            @Parameter(description = "일기 고유 id", example = "12345")
            @PathVariable("noteId") long noteId,
            @RequestBody @Valid RequestNoteDto requestNoteDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTE_UPDATE_SUCCESS, noteService.updateNote(noteId, requestNoteDto));
    }

    @Operation(summary = "일기 조회", description = "일기 고유 id(noteId)를 전송하여 해당 일기를 조회한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/note/{noteId}")
    @HasUserRole
    ApiResponse<ResponseNoteDto> getNote(
            @Parameter(description = "일기 고유 id", example = "12345")
            @PathVariable("noteId") long noteId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, noteService.findNote(noteId));
    }

    @Operation(summary = "일기 삭제", description = "일기 고유 id(noteId)를 전송하여 해당 일기를 삭제한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/note/{noteId}")
    @HasUserRole
    ApiResponse<Boolean> deleteNote(
            @Parameter(description = "일기 고유 id", example = "12345")
            @PathVariable("noteId") long noteId) {
        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, noteService.deleteNote(noteId));
    }


}
