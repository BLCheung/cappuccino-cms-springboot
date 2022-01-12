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

    public ForbiddenException() {
        this(Code.FORBIDDEN.getDesc());
    }

    public ForbiddenException(Integer code) {
        this(code, Code.FORBIDDEN.getDesc());
    }

    public ForbiddenException(String message) {
        this(Code.FORBIDDEN.getCode(), message);
    }

    public ForbiddenException(Integer code, String message) {
        super(code, message, HttpStatus.FORBIDDEN.value());
    }
}
