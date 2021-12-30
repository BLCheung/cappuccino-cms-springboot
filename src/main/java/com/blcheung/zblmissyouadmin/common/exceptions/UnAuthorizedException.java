package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * 认证异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:31 下午
 */
public class UnAuthorizedException extends HttpException {

    public UnAuthorizedException(String message) {
        super(message);
        this.code       = Code.UNAUTHORIZED.getCode();
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public UnAuthorizedException(Integer code) {
        super(Code.NOT_FOUND.getDesc());
        this.code       = code;
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public UnAuthorizedException(Integer code, String message) {
        super(code, message, HttpStatus.UNAUTHORIZED.value());
    }
}
