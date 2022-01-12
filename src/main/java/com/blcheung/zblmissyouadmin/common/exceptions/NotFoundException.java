package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 资源未找到异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:31 下午
 */
public class NotFoundException extends HttpException {

    @Serial
    private static final long serialVersionUID = 2166639156003846971L;

    public NotFoundException() {
        this(Code.NOT_FOUND.getDesc());
    }

    public NotFoundException(Integer code) {
        this(code, Code.NOT_FOUND.getDesc());
    }

    public NotFoundException(String message) {
        this(Code.NOT_FOUND.getCode(), message);
    }

    public NotFoundException(Integer code, String message) {
        super(code, message, HttpStatus.NOT_FOUND.value());
    }
}
