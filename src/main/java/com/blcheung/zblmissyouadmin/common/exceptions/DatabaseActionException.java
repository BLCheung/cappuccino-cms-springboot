package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 数据库操作失败异常
 *
 * @author BLCheung
 * @date 2021/12/27 7:37 下午
 */
public class DatabaseActionException extends HttpException {

    @Serial
    private static final long serialVersionUID = -1879376197878595016L;

    public DatabaseActionException() {
        this(Code.FAIL.getDesc());
    }

    public DatabaseActionException(Integer code) {
        this(code, Code.FAIL.getDesc());
    }

    public DatabaseActionException(String message) {
        this(Code.FAIL.getCode(), message);
    }

    public DatabaseActionException(Integer code, String message) {
        super(code, message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
