package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * @author BLCheung
 * @date 2021/12/30 11:02 下午
 */
public class ParameterException extends HttpException {
    public ParameterException(String message) {
        super(message);
        this.code       = Code.PARAMETER_ERROR.getCode();
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }

    public ParameterException(Integer code) {
        super(Code.PARAMETER_ERROR.getDesc());
        this.code       = code;
        this.statusCode = HttpStatus.BAD_REQUEST.value();
    }


    public ParameterException(Integer code, String message) {
        super(code, message, HttpStatus.BAD_REQUEST.value());
    }
}
