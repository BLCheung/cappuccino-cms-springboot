package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * 令牌无效异常
 *
 * @author BLCheung
 * @date 2021/12/15 1:42 上午
 */
public class TokenInvalidException extends HttpException {

    public TokenInvalidException(String message) {
        super(message);
        this.code       = Code.TOKEN_INVALID.getCode();
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public TokenInvalidException(Integer code) {
        super(Code.TOKEN_INVALID.getDesc());
        this.code       = code;
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public TokenInvalidException(Integer code, String message) {
        super(code, message, HttpStatus.UNAUTHORIZED.value());
    }
}
