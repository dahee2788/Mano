package maumnote.mano.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import maumnote.mano.dto.*;
import maumnote.mano.global.ResponseMessage;
import maumnote.mano.global.anotation.HasUserRole;
import maumnote.mano.service.NoteService;
import maumnote.mano.service.NotebookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "일기장", description = "일기장 관리 api")
@RestController
@RequiredArgsConstructor
public class NotebookController {

    private final NotebookService notebookService;
    private final NoteService noteService;

    @Operation(summary = "일기장 생성", description = "이름(name)을 입력하여 새 일기장을 만든다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기장 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/notebook")
    @HasUserRole
    ApiResponse<ResponseNotebookDto> createNotebook(@RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_CREATE_SUCCESS, notebookService.createNotebook(notebookDto));
    }

    @Operation(summary = "일기장 조회", description = "본인 소유의 일기장을 조회한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기장 조회 성공"),
    })
    @GetMapping("/notebook")
    @HasUserRole
    ApiResponse<List<ResponseNotebookDto>> getNotebooks() {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, notebookService.findAllNotebook());
    }

    @Operation(summary = "일기장 안의 일기들 조회", description = "하나의 일기장에 포함된 일기를 조회한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/notebook/{notebookId}")
    @HasUserRole
    ApiResponse<List<ResponseNoteDto>> getNotesByNotebookId(
            @Parameter(description = "일기장 고유 id", required = true, example = "1234")
            @PathVariable("notebookId") long notebookId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.SUCCESS, noteService.findAllNoteByNotebookId(notebookId));
    }

    @Operation(summary = "일기장 수정", description = "일기장 고유 id(notebookId)와 함께 변경 내용을 전달하여 일기장을 수정한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/notebook/{notebookId}")
    @HasUserRole
    ApiResponse<ResponseNotebookDto> updateNotebook(
            @Parameter(description = "일기장 고유 id", required = true, example = "1234")
            @PathVariable("notebookId") long notebookId,
            @RequestBody @Valid RequestNotebookDto notebookDto) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_UPDATE_SUCCESS, notebookService.updateNotebook(notebookId, notebookDto));
    }

    @Operation(summary = "일기장 삭제", description = "일기장 고유 id(notebookId)를 전달하여 일기장을 삭제한다.", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "일기 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "잘못된 형식", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/notebook/{notebookId}")
    @HasUserRole
    ApiResponse<Boolean> deleteNotebook(
            @Parameter(description = "일기장 고유 id", required = true, example = "1234")
            @PathVariable("notebookId") long notebookId) {

        return ApiResponse.response(HttpStatus.OK.value(), ResponseMessage.NOTEBOOK_DELETE_SUCCESS, notebookService.deleteNotebook(notebookId));
    }


}
