package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 令牌过期异常
 *
 * @author BLCheung
 * @date 2021/12/15 1:22 上午
 */
public class TokenExpiredException extends HttpException {

    @Serial
    private static final long serialVersionUID = 1882727199241071921L;

    protected Integer code = Code.TOKEN_EXPIRED.getCode();

    protected Integer statusCode = HttpStatus.UNAUTHORIZED.value();

    public TokenExpiredException() {
        this(Code.TOKEN_EXPIRED.getCode());
    }

    public TokenExpiredException(Integer code) {
        super(code, Code.TOKEN_EXPIRED.getDesc());
    }

    public TokenExpiredException(String message) {
        super(Code.TOKEN_EXPIRED.getCode(), message);
        this.isDefaultMsg = false;
    }

    public TokenExpiredException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }
}
