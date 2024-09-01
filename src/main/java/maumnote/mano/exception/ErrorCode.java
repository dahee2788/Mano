package maumnote.mano.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    NOTEBOOK_CREATE_FAIL("일기장 만들기에 실패했습니다. 다시 시도해 보세요."),
    VALIDATION_ERROR("유효성 검사 실패"),
    INVALID_FORMAT("잘못된 형식"),
    JOIN_FAIL("회원가입에 실패하였습니다. 다시 시도해 주세요."),
    GENERAL_LOGIN_FAIL("로그인에 실패하였습니다. 이메일 주소 또는 비밀번호를 확인해 주세요."),
    ELEMENT_NOT_FOUND("해당 요소가 존재하지 않습니다.");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
