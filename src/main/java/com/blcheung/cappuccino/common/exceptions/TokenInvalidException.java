package com.blcheung.cappuccino.common.exceptions;

import com.blcheung.cappuccino.common.Code;
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

    protected Integer code = Code.TOKEN_INVALID.getCode();

    protected Integer statusCode = HttpStatus.UNAUTHORIZED.value();

    public TokenInvalidException() {
        this(Code.TOKEN_INVALID.getCode());
    }

    public TokenInvalidException(Integer code) {
        super(code, Code.TOKEN_INVALID.getDesc());
    }

    public TokenInvalidException(String message) {
        super(Code.TOKEN_INVALID.getCode(), message);
        this.isDefaultMsg = false;
    }

    public TokenInvalidException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }
}
