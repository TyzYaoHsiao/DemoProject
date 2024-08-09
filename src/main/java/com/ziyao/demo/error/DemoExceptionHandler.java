package com.ziyao.demo.error;

import com.ziyao.demo.api.model.res.ResponseEntity;
import com.ziyao.demo.constant.MessageConst;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

/**
 * 全域異常處理器
 */
@Slf4j
@RestControllerAdvice
public class DemoExceptionHandler {

    /**
     * spring validation 欄位檢核錯誤
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("%s.%s: %s",
                        fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.joining("\n"));
        return returnResponse(new DemoException(MessageConst.RtnCode.FIELD_ERROR, errorMessage));
    }

    /**
     * 自定義錯誤處理
     *
     * @param demoException
     * @return
     */
    @ExceptionHandler(DemoException.class)
    public Object handleDemoException(DemoException demoException) {
        return returnResponse(demoException);
    }

    /**
     * 日期格式轉換錯誤
     *
     * @param dateTimeParseException
     * @return
     */
    @ExceptionHandler(DateTimeParseException.class)
    public Object handleException(DateTimeParseException dateTimeParseException) {
        return returnResponse(new DemoException(MessageConst.RtnCode.DATE_FORMAT_ERROR, dateTimeParseException.getParsedString()));
    }

    /**
     * 例外處理
     *
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return returnResponse(new DemoException(MessageConst.RtnCode.SYSTEM_ERROR));
    }

    /**
     * 404 資源不存在
     *
     * @param e
     * @param request
     * @return
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public Object handleNoResourceFoundException(NoResourceFoundException e, HttpServletRequest request) {
        return returnResponse(new DemoException(MessageConst.RtnCode.NOT_FOUND, request.getRequestURI()));
    }

    /**
     * 405 方法不支援
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Object handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        return returnResponse(new DemoException(MessageConst.RtnCode.METHOD_NOT_ALLOWED, e.getMethod()));
    }

    private ResponseEntity<Object> returnResponse(DemoException e) {
        return ResponseEntity.builder()
                .code(e.getCode())
                .msg(e.getMsg())
                .build();
    }
}
