package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

/**
 * 数据库操作失败异常
 *
 * @author BLCheung
 * @date 2021/12/27 7:37 下午
 */
public class DatabaseActionException extends HttpException {

    public DatabaseActionException(Integer code) {
        super(code, Code.FAIL.getDesc(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public DatabaseActionException(String message) {
        super(Code.FAIL.getCode(), message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    public DatabaseActionException(Integer code, Integer statusCode) {
        super(code, Code.FAIL.getDesc(), statusCode);
    }

    public DatabaseActionException(Integer code, String message, Integer statusCode) {
        super(code, message, statusCode);
    }
}
