package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 文件体积太大异常
 *
 * @author BLCheung
 * @date 2022/1/16 3:23 上午
 */
public class FileTooLargeException extends HttpException {

    @Serial
    private static final long serialVersionUID = 8788264037524133199L;

    public FileTooLargeException() {
        this(Code.FILE_TOO_LARGE.getDesc());
    }

    public FileTooLargeException(Integer code) {
        this(code, Code.FILE_TOO_LARGE.getDesc());
    }

    public FileTooLargeException(String message) {
        this(Code.FILE_TOO_LARGE.getCode(), message);
    }

    public FileTooLargeException(Integer code, String message) {
        super(code, message, HttpStatus.PAYLOAD_TOO_LARGE.value());
    }
}
