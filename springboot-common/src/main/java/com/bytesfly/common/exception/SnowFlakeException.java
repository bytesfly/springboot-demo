package com.bytesfly.common.exception;

/**
 * 雪花算法生成ID异常
 */
public class SnowFlakeException extends RuntimeException {

    public SnowFlakeException(String message) {
        super(message);
    }

    public SnowFlakeException(String message, Throwable cause) {
        super(message, cause);
    }
}
