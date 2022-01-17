package com.blcheung.zblmissyouadmin.common.exceptions;

import com.blcheung.zblmissyouadmin.common.Code;
import org.springframework.http.HttpStatus;

import java.io.Serial;

/**
 * 文件扩展名异常
 *
 * @author BLCheung
 * @date 2022/1/16 3:23 上午
 */
public class FileExtensionException extends HttpException {

    @Serial
    private static final long serialVersionUID = -5546159435906998544L;

    public FileExtensionException() {
        this(Code.FILE_EXT_ERROR.getDesc());
    }

    public FileExtensionException(Integer code) {
        this(code, Code.FILE_EXT_ERROR.getDesc());
    }

    public FileExtensionException(String message) {
        this(Code.FILE_EXT_ERROR.getCode(), message);
    }

    public FileExtensionException(Integer code, String message) {
        super(code, message, HttpStatus.NOT_ACCEPTABLE.value());
    }
}
