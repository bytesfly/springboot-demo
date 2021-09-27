package com.bytesfly.validation.component;

import cn.hutool.core.collection.CollUtil;
import com.bytesfly.common.exception.AuthException;
import com.bytesfly.common.exception.BizException;
import com.bytesfly.validation.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@Slf4j
@ResponseBody
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse methodNotSupportedExceptionHandler(HttpServletRequest request,
                                                          HttpRequestMethodNotSupportedException e) {
        logError(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), request, e);

        return ApiResponse.failed(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED.value());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse methodArgumentNotValidExceptionHandler(HttpServletRequest request,
                                                              MethodArgumentNotValidException e) {
        logError("参数异常", request, e);

        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        List<String> errorList = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                .collect(Collectors.toList());

        return ApiResponse.failed(CollUtil.join(errorList, ";"), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse constraintViolationExceptionHandler(HttpServletRequest request,
                                                           ConstraintViolationException e) {
        logError("参数异常", request, e);

        List<String> errors = e.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        return ApiResponse.failed(CollUtil.join(errors, ";"), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse authExceptionHandler(HttpServletRequest request,
                                            AuthException e) {
        logError("身份验证异常", request, e);

        return ApiResponse.failed(e.getMessage(), HttpStatus.UNAUTHORIZED.value());
    }

    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse bizExceptionHandler(HttpServletRequest request,
                                           BizException e) {
        logError("业务异常", request, e);

        return ApiResponse.failed(e.getMessage(), HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse exceptionHandler(HttpServletRequest request,
                                        Throwable e) {
        logError("未知异常", request, e);

        return ApiResponse.failed(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    private static void logError(String errorType, HttpServletRequest request, Throwable e) {
        log.error("{}, uri: {}, method: {}, error: {}",
                errorType, request.getRequestURI(), request.getMethod(), e);
    }
}
