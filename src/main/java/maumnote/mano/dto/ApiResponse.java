package maumnote.mano.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int statusCode;
    private String responseMsg;
    private T data;

    public ApiResponse(int statusCode, String responseMsg) {
        super();
        this.statusCode = statusCode;
        this.responseMsg = responseMsg;
        this.data = null;
    }

    public static<T> ApiResponse<T> response(final int statusCode, final String responseMsg) {
        return response(statusCode, responseMsg, null);

    }

    public static <T> ApiResponse<T> response(final int statusCode, final String responseMsg, final T t) {
        return ApiResponse.<T>builder().data(t).statusCode(statusCode).responseMsg(responseMsg).build();
    }

}