package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 文件太多异常
 *
 * @author BLCheung
 * @date 2022/1/16 3:23 上午
 */
public class FileTooManyException extends HttpException {

    @Serial
    private static final long serialVersionUID = -2602971869479051189L;

    protected Integer code = Code.FILE_TOO_MANY.getCode();

    protected Integer statusCode = HttpStatus.PAYLOAD_TOO_LARGE.value();

    public FileTooManyException() {
        this(Code.FILE_TOO_MANY.getCode());
    }

    public FileTooManyException(Integer code) {
        super(code, Code.FILE_TOO_MANY.getDesc());
    }

    public FileTooManyException(String message) {
        super(Code.FILE_TOO_MANY.getCode(), message);
        this.isDefaultMsg = false;
    }

    public FileTooManyException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }
}
