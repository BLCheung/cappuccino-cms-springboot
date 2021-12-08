package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * 资源未找到异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:31 下午
 */
public class NotFoundException extends HttpException {

    public NotFoundException(String message) {
        super(message);
        this.code       = Code.NOT_FOUND.getCode();
        this.statusCode = HttpStatus.NOT_FOUND.value();
    }

    public NotFoundException(Integer code) {
        super(Code.NOT_FOUND.getDesc());
        this.code       = code;
        this.statusCode = HttpStatus.NOT_FOUND.value();
    }

    public NotFoundException(Integer code, String message) {
        super(code, message, HttpStatus.NOT_FOUND.value());
    }
}
