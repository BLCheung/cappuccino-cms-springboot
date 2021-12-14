package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * 令牌过期异常
 *
 * @author BLCheung
 * @date 2021/12/15 1:22 上午
 */
public class TokenExpiredException extends HttpException {

    public TokenExpiredException(String message) {
        super(message);
        this.code       = Code.TOKEN_EXPIRED.getCode();
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public TokenExpiredException(Integer code) {
        super(Code.TOKEN_EXPIRED.getDesc());
        this.code       = code;
        this.statusCode = HttpStatus.UNAUTHORIZED.value();
    }

    public TokenExpiredException(Integer code, String message) {
        super(code, message, HttpStatus.UNAUTHORIZED.value());
    }
}
