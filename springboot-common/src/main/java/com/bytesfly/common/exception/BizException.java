package com.bytesfly.common.exception;

/**
 * 通用业务异常
 * 注意: 这里的 message 要求可读性高, 提示友好, 建议中文, 不能直接把异常堆栈信息直接放到这里的message里
 * 因为此处的 message 信息可能会直接返回给前端, 作为提示信息
 */
public class BizException extends RuntimeException {

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
}
