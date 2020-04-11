package com.dm.core.pojo;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * 响应对象。code、msg、data
 * <ul>
 * <li>处理成功一般返回处理结果和返回数据，失败只返回处理结果。具体返回什么需看接口文档。</li>
 * <li>处理成功结果码一般是200，失败码具体看出了什么错，对照 HTTP 响应码填。</li>
 * <li>默认处理方法慎用，前台最想要拿到的还是具体的结果码和信息。</li>
 * </ul>
 */
public class Result implements Serializable {
    /**
     * 默认成功响应码
     */
    private static final Integer DEAFAULT_SUCCESS_CODE = HttpStatus.OK.value();
    /**
     * 默认成功响应信息
     */
    private static final String DEAFAULT_SUCCESS_MSG = "请求/处理成功！";
    /**
     * 默认失败响应码
     */
    private static final Integer DEAFAULT_fail_CODE = HttpStatus.INTERNAL_SERVER_ERROR.value();
    /**
     * 默认失败响应信息
     */
    private static final String DEAFAULT_fail_MSG = "请求/处理失败！";

    /**
     * 响应状态码
     */
    @Getter
    private Integer code;

    /**
     * 返回消息
     */
    @Getter
    private String msg;

    /**
     * 返回数据
     */
    @Getter
    private Object data;


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓成功↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
    /**
     * 处理成功响应，返回自定义响应码、信息和数据。
     *
     * @param msg  处理结果信息
     * @return 响应对象
     */
    public static Result success(String msg) {
        Result result=new Result();
        result.code=DEAFAULT_SUCCESS_CODE;
        result.msg=msg;
        result.data = null;
        return result;
    }

    /**
     * 处理成功响应，返回自定义响应码、信息和数据。
     *
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result success(Object data) {
        Result result=new Result();
        result.code=DEAFAULT_SUCCESS_CODE;
        result.msg=DEAFAULT_SUCCESS_MSG;
        result.data = data;
        return result;
    }

    /**
     * 处理成功响应，返回自定义响应码、信息和数据。
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result  success(String msg, Object data) {
        Result result=new Result();
        result.code=DEAFAULT_SUCCESS_CODE;
        result.msg=msg;
        result.data = data;
        return result;
    }

    /**
     * 处理成功响应，返回自定义响应码、信息和数据。
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result success(HttpStatus httpStatus, String msg, Object data) {
        Result result=new Result();
        result.code=httpStatus.value();
        result.msg=msg;
        result.data = data;
        return result;
    }


    /*↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓失败↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/

    /**
     * 处理失败响应，返回自定义响应码、信息和数据。
     *
     * @param msg 处理结果信息
     * @return 响应对象
     */
    public static Result fail(String msg) {
        Result result=new Result();
        result.code=DEAFAULT_fail_CODE;
        result.msg=msg;
        result.data = null;
        return result;
    }

    /**
     * 处理失败响应，返回自定义响应码、信息和数据。
     *
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result fail(Object data) {
        Result result=new Result();
        result.code=DEAFAULT_fail_CODE;
        result.msg=DEAFAULT_fail_MSG;
        result.data = data;
        return result;
    }

    /**
     * 处理失败响应，返回自定义响应码、信息和数据。
     *
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result fail(String msg, Object data) {
        Result result=new Result();
        result.code=DEAFAULT_fail_CODE;
        result.msg=msg;
        result.data = data;
        return result;
    }

    /**
     * 处理失败响应，返回自定义响应码、信息和数据。
     *
     * @param httpStatus HTTP 响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result fail(HttpStatus httpStatus, String msg, Object data) {
        Result result=new Result();
        result.code=httpStatus.value();
        result.msg=msg;
        result.data = data;
        return result;
    }

    /**
     * 处理失败响应，返回自定义响应码、信息和数据。
     *
     * @param code  响应码
     * @param msg  处理结果信息
     * @param data 返回数据
     * @return 响应对象
     */
    public static Result fail(Integer code, String msg, Object data) {
        Result result=new Result();
        result.code=code;
        result.msg=msg;
        result.data = data;
        return result;
    }
}
