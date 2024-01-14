package com.leis.hxds.bff.customer.config;


import cn.dev33.satoken.exception.NotLoginException;
import cn.hutool.json.JSONObject;
import com.leis.hxds.common.exception.HxdsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public String ExceptionHandler(Exception e) {
        JSONObject jsonObject = new JSONObject();
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            jsonObject.set("error", exception.getBindingResult().getFieldError().getDefaultMessage());
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            jsonObject.set("error", "Web方法不支持当前的请求类型");
        } else if (e instanceof HttpMessageNotReadableException) {
            jsonObject.set("error", "缺少提交的数据");
        } else if (e instanceof HxdsException) {
            log.error("执行异常", e);
            HxdsException exception = (HxdsException) e;
            jsonObject.set("error", exception.getMsg());
        } else {
            log.error("执行异常", e);
            jsonObject.set("error", "执行异常");
        }
        return jsonObject.toString();
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotLoginException.class)
    public String unLoginHandler(Exception e) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("error", e.getMessage());
        return jsonObject.toString();
    }
}
