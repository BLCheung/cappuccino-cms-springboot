package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * HTTP异常
 *
 * @author BLCheung
 * @date 2021/12/8 1:31 上午
 */
public class HttpException extends RuntimeException implements BaseResponse {

    @Serial
    private static final long    serialVersionUID = 3634412969489117410L;
    
    protected            int     code;
    protected            Integer statusCode       = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public HttpException() {
        super(Code.INTERNAL_SERVER_ERROR.getDesc());
        this.code = Code.INTERNAL_SERVER_ERROR.getCode();
    }

    public HttpException(String message) {
        super(message);
        this.code = Code.INTERNAL_SERVER_ERROR.getCode();
    }

    public HttpException(Integer code) {
        super(Code.INTERNAL_SERVER_ERROR.getDesc());
        this.code = code;
    }

    public HttpException(Integer code, Integer statusCode) {
        super(Code.INTERNAL_SERVER_ERROR.getDesc());
        this.code       = code;
        this.statusCode = statusCode;
    }

    public HttpException(Integer code, String message, Integer statusCode) {
        super(message);
        this.code       = code;
        this.statusCode = statusCode;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }
}
