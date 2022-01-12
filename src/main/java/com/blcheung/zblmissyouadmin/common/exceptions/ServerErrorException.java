package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 服务器异常
 *
 * @author BLCheung
 * @date 2021/12/8 8:24 下午
 */
public class ServerErrorException extends HttpException {

    @Serial
    private static final long serialVersionUID = 8404987900707708713L;

    public ServerErrorException() {
        this(Code.INTERNAL_SERVER_ERROR.getDesc());
    }

    public ServerErrorException(Integer code) {
        this(code, Code.INTERNAL_SERVER_ERROR.getDesc());
    }

    public ServerErrorException(String message) {
        this(Code.INTERNAL_SERVER_ERROR.getCode(), message);
    }

    public ServerErrorException(Integer code, String message) {
        super(code, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
