package maumnote.mano.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    NOTEBOOK_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "일기장 만들기에 실패했습니다. 다시 시도해 보세요."),
    NOTE_CREATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "일기 작성에 실패했습니다. 다시 시도해 보세요."),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "잘못된 형식"),
    JOIN_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "회원가입에 실패하였습니다. 다시 시도해 주세요."),
    GENERAL_LOGIN_FAIL(HttpStatus.BAD_REQUEST, "로그인에 실패하였습니다. 이메일 주소 또는 비밀번호를 확인해 주세요."),
    ELEMENT_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "해당 요소가 존재하지 않습니다."),
    DATA_INTEGRITY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "데이터의 무결성 제약 조건이 위반되었습니다."),
    EMPTY_REQUEST_BODY(HttpStatus.BAD_REQUEST, "빈 요청 본문"),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST, "토큰이 만료되었습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다."),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류가 발생했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "리소스에 대한 접근 권한이 없습니다."),
    DUPLICATE_MEMBER_EMAIL(HttpStatus.BAD_REQUEST, "이미 사용중인 이메일이 있습니다."),
    INTERNAL_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 처리 오류");

    private final HttpStatusCode httpStatusCode;
    private final String message;

}
