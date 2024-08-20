package maumnote.mano.controller;

import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import maumnote.mano.service.NotebookService;

@RestController
public class NotebookController {

    private final NotebookService notebookService;

    public NotebookController(NotebookService notebookService) {
        this.notebookService = notebookService;
    }
    @PostMapping("/notebook")
    ResponseEntity<ApiResponse<ResponseNotebookDto>> saveNotebook(@RequestBody RequestNotebookDto notebookDto) {
        ApiResponse<ResponseNotebookDto> response = null;
        if(ObjectUtils.isEmpty(notebookDto) || ObjectUtils.isEmpty(notebookDto.getName())) {
            response = ApiResponse.response(HttpStatus.BAD_REQUEST.value(), "일기장 이름을 확인해주세요.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        ResponseNotebookDto result = notebookService.save(notebookDto);
        if(ObjectUtils.isEmpty(result)) response = ApiResponse.response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "일기장 등록에 실패하였습니다.");
        else response = ApiResponse.response(HttpStatus.OK.value(), "일기장이 등록 되었습니다.",result);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
