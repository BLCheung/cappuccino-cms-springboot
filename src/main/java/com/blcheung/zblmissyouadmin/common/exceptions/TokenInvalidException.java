package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 令牌无效异常
 *
 * @author BLCheung
 * @date 2021/12/15 1:42 上午
 */
public class TokenInvalidException extends HttpException {

    @Serial
    private static final long serialVersionUID = 6633425654713859604L;

    public TokenInvalidException() {
        this(Code.TOKEN_INVALID.getDesc());
    }

    public TokenInvalidException(Integer code) {
        this(code, Code.TOKEN_INVALID.getDesc());
    }

    public TokenInvalidException(String message) {
        this(Code.TOKEN_INVALID.getCode(), message);
    }

    public TokenInvalidException(Integer code, String message) {
        super(code, message, HttpStatus.UNAUTHORIZED.value());
    }
}
