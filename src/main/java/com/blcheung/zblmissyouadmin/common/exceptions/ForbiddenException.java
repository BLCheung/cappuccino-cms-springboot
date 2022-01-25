package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 禁止操作异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:31 下午
 */
public class ForbiddenException extends HttpException {

    @Serial
    private static final long serialVersionUID = -7040854574781587444L;

    protected Integer code = Code.FORBIDDEN.getCode();

    protected Integer statusCode = HttpStatus.FORBIDDEN.value();

    public ForbiddenException() {
        this(Code.FORBIDDEN.getCode());
    }

    public ForbiddenException(Integer code) {
        super(code, Code.FORBIDDEN.getDesc());
    }

    public ForbiddenException(String message) {
        super(Code.FORBIDDEN.getCode(), message);
        this.isDefaultMsg = false;
    }

    public ForbiddenException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }
}
