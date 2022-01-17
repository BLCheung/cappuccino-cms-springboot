package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 通用操作失败异常
 *
 * @author BLCheung
 * @date 2022/1/16 3:17 上午
 */
public class FailedException extends HttpException {

    @Serial
    private static final long serialVersionUID = 4624766117100079482L;

    public FailedException() {
        this(Code.FAIL.getDesc());
    }

    public FailedException(Integer code) {
        this(code, Code.FAIL.getDesc());
    }

    public FailedException(String message) {
        this(Code.FAIL.getCode(), message);
    }

    public FailedException(Integer code, String message) {
        super(code, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
