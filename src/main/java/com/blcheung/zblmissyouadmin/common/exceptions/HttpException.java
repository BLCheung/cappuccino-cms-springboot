package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.interfaces.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.io.Serial;

/**
 * HTTP异常
 *
 * @author BLCheung
 * @date 2021/12/8 1:31 上午
 */
public abstract class HttpException extends RuntimeException implements BaseResponse {

    @Serial
    private static final long serialVersionUID = 3634412969489117410L;

    protected Integer code;

    protected String msg;

    protected Integer statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    // 是否默认消息提示
    protected Boolean isDefaultMsg = true;

    public HttpException() {
        this(Code.INTERNAL_SERVER_ERROR.getCode());
    }

    public HttpException(Integer code) {
        super(Code.INTERNAL_SERVER_ERROR.getDesc());
        this.code = code;
    }

    public HttpException(String message) {
        this(Code.INTERNAL_SERVER_ERROR.getCode(), message);
        this.msg          = message;
        this.isDefaultMsg = false;
    }

    public HttpException(Integer code, String message) {
        super(message);
        this.msg  = message;
        this.code = code;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
