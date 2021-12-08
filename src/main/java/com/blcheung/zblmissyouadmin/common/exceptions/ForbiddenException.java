package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * @author BLCheung
 * @date 2021/12/8 8:31 下午
 */
public class ForbiddenException extends HttpException {

    public ForbiddenException(String message) {
        super(message);
        this.code       = Code.FORBIDDEN.getCode();
        this.statusCode = HttpStatus.FORBIDDEN.value();
    }

    public ForbiddenException(Integer code) {
        super(Code.FORBIDDEN.getDesc());
        this.code       = code;
        this.statusCode = HttpStatus.FORBIDDEN.value();
    }

    public ForbiddenException(Integer code, String message) {
        super(code, message, HttpStatus.FORBIDDEN.value());
    }
}
