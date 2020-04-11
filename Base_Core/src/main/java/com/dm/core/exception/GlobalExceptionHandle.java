package com.dm.core.exception;

import com.dm.core.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import javax.validation.ValidationException;
import java.util.List;

/**
 * 全局异常处理
 */
@Slf4j
@EnableWebMvc
@RestControllerAdvice
public class GlobalExceptionHandle {
    /**
     * 400 - Bad Request
     */
    @ResponseBody
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        String msg = "缺少请求参数！";
        log.error(msg, e);
        return Result.fail(HttpStatus.BAD_REQUEST,msg,null);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        String msg = e.getMessage();
        log.error("参数解析失败：", e);
        return Result.fail(HttpStatus.BAD_REQUEST,msg,null);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.error("方法参数无效: ", e);
        return Result.fail(HttpStatus.BAD_REQUEST,msg,null);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        String msg = handleBindingResult(e.getBindingResult());
        log.error("参数绑定失败:", e);
        return Result.fail(HttpStatus.BAD_REQUEST,msg,null);
    }

    /**
     * 400 - Bad Request
     */
    @ResponseBody
    @ExceptionHandler(ValidationException.class)
    public Result handleValidationException(ValidationException e) {
        String msg = e.getMessage();
        log.error("参数验证失败：", e);
        return Result.fail(HttpStatus.BAD_REQUEST,msg,null);
    }

    /**
     * 401 - Unauthorized
     */
    @ResponseBody
    @ExceptionHandler(LoginException.class)
    public Result handleLoginException(LoginException e) {
        String msg = e.getMessage();
        log.error("用户未登录：", e);
        return Result.fail(HttpStatus.UNAUTHORIZED, msg, null);
    }

    /**
     * 403 - Unauthorized
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UnauthorizedException.class)
    public Result handleLoginException(UnauthorizedException e) {
        String msg = e.getMessage();
        log.error("用户无权限：", e);
        return Result.fail(HttpStatus.FORBIDDEN, "用户无权限!", null);
    }

    /**
     * 404 - NOT_FOUND
     */
    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public Result handleLoginException(NoHandlerFoundException e) {
        String msg = e.getMessage();
        log.error("未找到该方法：", e);
        return Result.fail(HttpStatus.NOT_FOUND, "未找到该方法!", null);
    }


    /**
     * 405 - Method Not Allowed
     */
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String msg = "不支持当前请求类型！";
        log.error(msg, e);
        return Result.fail(HttpStatus.METHOD_NOT_ALLOWED,msg,null);
    }

    /**
     * 422 - UNPROCESSABLE_ENTITY
     */
    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result handleMaxUploadSizeExceededException(Exception e) {
        String msg = "所上传文件大小超过最大限制，上传失败！";
        log.error(msg, e);
        return Result.fail(HttpStatus.UNPROCESSABLE_ENTITY,msg,null);
    }

    /**
     * 500 - Internal Server Error
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        String msg = "服务内部异常！" + e.getMessage();
        log.error(msg, e);
        return Result.fail(msg);
    }

    /**
     * 处理参数绑定异常，并拼接出错的参数异常信息。
     */
    private String handleBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            final List<FieldError> fieldErrors = result.getFieldErrors();
            return fieldErrors.iterator().next().getDefaultMessage();
        }
        return null;
    }
}
