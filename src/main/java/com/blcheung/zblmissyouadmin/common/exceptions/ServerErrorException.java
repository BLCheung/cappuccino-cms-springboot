package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * 服务器异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:24 下午
 */
public class ServerErrorException extends HttpException {

    public ServerErrorException(Integer code) {
        super(code, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public ServerErrorException(String message) {
        super(message);
        this.code       = Code.INTERNAL_SERVER_ERROR.getCode();
        this.statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
    }

    public ServerErrorException(Integer code, String message) {
        super(code, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
