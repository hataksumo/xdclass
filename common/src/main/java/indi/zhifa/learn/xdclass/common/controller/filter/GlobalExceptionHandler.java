package indi.zhifa.learn.xdclass.common.controller.filter;

import com.fasterxml.jackson.core.JsonParseException;
import indi.zhifa.learn.xdclass.common.entity.RestResponse;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理类
 *
 * @author wangxuyang
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public RestResponse argumentValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        String errorMes = "参数验证失败:";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMes += fieldError.getDefaultMessage() + ", ";
        }
        errorMes = errorMes.substring(0, errorMes.length() - 2);
        log.error("消息平台：服务参数校验失败:{}", errorMes);
        return RestResponse.clientError(errorMes);
    }

    @ExceptionHandler(JsonParseException.class)
    @ResponseBody
    public RestResponse jsonValidException() {
        return RestResponse.clientError("Json格式错误!");
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public RestResponse otherException(ServiceException ex) {
        return RestResponse.serverError(ex.getCode(),"服务器逻辑错误，错误信息是 " + ex.getMsg());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public RestResponse handle(Exception e) {
        e.printStackTrace();
        return RestResponse.error(e.toString());
    }
}
