package com.dm.core.exception;

/**
 * @author ：leigq
 * @date ：2019/7/1 09:26
 * @description：登录异常
 */
public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super();
    }

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
