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

    protected Integer code = Code.FILE_EXT_ERROR.getCode();

    protected Integer statusCode = HttpStatus.NOT_ACCEPTABLE.value();

    public FileExtensionException() {
        this(Code.FILE_EXT_ERROR.getCode());
    }

    public FileExtensionException(Integer code) {
        super(code, Code.FILE_EXT_ERROR.getDesc());
    }

    public FileExtensionException(String message) {
        super(Code.FILE_EXT_ERROR.getCode(), message);
        this.isDefaultMsg = false;
    }

    public FileExtensionException(Integer code, String message) {
        super(code, message);
        this.isDefaultMsg = false;
    }
}
