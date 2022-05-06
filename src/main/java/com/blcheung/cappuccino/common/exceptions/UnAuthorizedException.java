package com.blcheung.cappuccino.common.exceptions;

import com.blcheung.cappuccino.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 认证异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:31 下午
 */
public class UnAuthorizedException extends HttpException {

    @Serial
    private static final long serialVersionUID = -6511277210921214106L;

    protected Integer code = Code.UNAUTHORIZED.getCode();

    protected Integer statusCode = HttpStatus.UNAUTHORIZED.value();

    public UnAuthorizedException() {
        this(Code.UNAUTHORIZED.getCode());
    }

    public UnAuthorizedException(Integer code) {
        super(code, Code.UNAUTHORIZED.getDesc());
    }

    public UnAuthorizedException(String message) {
        super(Code.UNAUTHORIZED.getCode(), message);
        this.isDefaultMsg = false;
    }

    public UnAuthorizedException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }
}
