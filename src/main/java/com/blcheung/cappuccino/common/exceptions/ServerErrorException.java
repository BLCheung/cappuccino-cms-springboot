package com.blcheung.cappuccino.common.exceptions;

import com.blcheung.cappuccino.common.Code;
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

    protected Integer code = Code.INTERNAL_SERVER_ERROR.getCode();

    protected Integer statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public ServerErrorException() {
        this(Code.INTERNAL_SERVER_ERROR.getCode());
    }

    public ServerErrorException(Integer code) {
        super(code, Code.INTERNAL_SERVER_ERROR.getDesc());
    }

    public ServerErrorException(String message) {
        super(Code.INTERNAL_SERVER_ERROR.getCode(), message);
        this.isDefaultMsg = false;
    }

    public ServerErrorException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }
}
