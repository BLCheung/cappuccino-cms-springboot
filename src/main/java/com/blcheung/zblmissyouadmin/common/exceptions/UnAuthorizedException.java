package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
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

    public UnAuthorizedException() {
        this(Code.UNAUTHORIZED.getDesc());
    }

    public UnAuthorizedException(String message) {
        this(Code.UNAUTHORIZED.getCode(), message);
    }

    public UnAuthorizedException(Integer code) {
        this(code, Code.UNAUTHORIZED.getDesc());
    }

    public UnAuthorizedException(Integer code, String message) {
        super(code, message, HttpStatus.UNAUTHORIZED.value());
    }
}
