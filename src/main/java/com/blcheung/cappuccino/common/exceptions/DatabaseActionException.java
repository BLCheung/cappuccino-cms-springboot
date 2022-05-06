package com.blcheung.cappuccino.common.exceptions;

import com.blcheung.cappuccino.common.Code;
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

    protected Integer code = Code.FAIL.getCode();

    protected Integer statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();

    public DatabaseActionException() {
        this(Code.FAIL.getCode());
    }

    public DatabaseActionException(Integer code) {
        super(code, Code.FAIL.getDesc());
    }

    public DatabaseActionException(String message) {
        super(Code.FAIL.getCode(), message);
        this.isDefaultMsg = false;
    }

    public DatabaseActionException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }

    @Override
    public Integer getStatusCode() {
        return this.statusCode;
    }
}
