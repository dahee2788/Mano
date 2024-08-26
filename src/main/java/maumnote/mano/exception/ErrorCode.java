package maumnote.mano.exception;


import lombok.Getter;

@Getter
public enum ErrorCode {
    NOTEBOOK_CREATE_FAIL("일기장 만들기에 실패했습니다. 다시 시도해 보세요."),
    VALIDATION_ERROR("유효성 검사 실패"),
    INVALID_FORMAT("잘못된 형식");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
