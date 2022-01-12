package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 参数错误异常
 *
 * @author BLCheung
 * @date 2021/12/30 11:02 下午
 */
public class ParameterException extends HttpException {

    @Serial
    private static final long serialVersionUID = 4165158781838308373L;

    public ParameterException() {
        this(Code.PARAMETER_ERROR.getDesc());
    }

    public ParameterException(Integer code) {
        this(code, Code.PARAMETER_ERROR.getDesc());
    }

    public ParameterException(String message) {
        this(Code.PARAMETER_ERROR.getCode(), message);
    }

    public ParameterException(Integer code, String message) {
        super(code, message, HttpStatus.BAD_REQUEST.value());
    }
}
