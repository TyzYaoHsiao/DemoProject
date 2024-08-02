package com.demo.error;

import com.demo.api.model.res.ResponseEntity;
import com.demo.constant.MessageConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.format.DateTimeParseException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleValidationError(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage());
        BindingResult result = ex.getBindingResult();

        StringBuilder errorMessage = new StringBuilder();
        for (FieldError fieldError : result.getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage()).append(". ");
        }
        return returnResponse(new CustomException(MessageConst.RtnCode.FIELD_ERROR, errorMessage.toString()));
    }
    /**
     * 自定義錯誤處理
     *
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    public Object handleCustomException(CustomException customException) {
        log.warn(customException.getMessage());
        return returnResponse(customException);
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
        return returnResponse(exception);
    }

    /**
     * 日期格式轉換錯誤
     *
     * @param dateTimeParseException
     * @return
     */
    @ExceptionHandler(DateTimeParseException.class)
    public Object handleException(DateTimeParseException dateTimeParseException) {
        log.error(dateTimeParseException.getMessage());
        return returnResponse(new CustomException(MessageConst.RtnCode.SYSTEM_ERROR, MessageConst.PARSE_DATE_ERROR + " : " + dateTimeParseException.getParsedString()));
    }

    private Object returnResponse(Exception e) {
        return returnResponse(new CustomException(MessageConst.RtnCode.SYSTEM_ERROR, MessageConst.SYSTEM_ERROR));
    }

    private ResponseEntity<Object> returnResponse(CustomException e) {
        return ResponseEntity.builder()
                .code(e.getCode())
                .msg(e.getMsg())
                .result(new Object())
                .build();
    }
}
