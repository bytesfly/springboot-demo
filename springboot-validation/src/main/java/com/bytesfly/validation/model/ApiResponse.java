package com.bytesfly.validation.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    @Builder.Default
    private int code = HttpStatus.OK.value();

    @Builder.Default
    private String msg = HttpStatus.OK.getReasonPhrase();

    @Builder.Default
    private Long total = 0L;

    private T result;

    // -------------静态方法--------------

    public static ApiResponse successful() {
        return ApiResponse.builder().build();
    }

    public static <T> ApiResponse<T> successful(T result) {
        return ApiResponse.<T>builder()
                .result(result)
                .build();
    }

    public static <T> ApiResponse<T> successful(T result, Long total) {
        return ApiResponse.<T>builder()
                .result(result)
                .total(total)
                .build();
    }

    public static <T> ApiResponse<T> successful(T result, String msg) {
        return ApiResponse.<T>builder()
                .result(result)
                .msg(msg)
                .build();
    }

    public static <T> ApiResponse<T> failed(T result, String msg, int code) {
        return ApiResponse.<T>builder()
                .result(result)
                .msg(msg)
                .code(code)
                .build();
    }

    public static ApiResponse failed(String msg, int code) {
        return ApiResponse.builder()
                .msg(msg)
                .code(code)
                .build();
    }
}
