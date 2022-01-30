package com.blcheung.zblmissyouadmin.vo.common;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.util.ResponseUtil;
import lombok.*;
import org.springframework.http.HttpStatus;

/**
 * @author BLCheung
 * @date 2021/12/1 11:34 下午
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultVO() {
        this(Code.OK.getCode(), Code.OK.getDesc(), null);
    }

    public ResultVO(T data) {
        this(Code.OK.getCode(), Code.OK.getDesc(), data);
    }

    public ResultVO(Integer code) {
        this(code, null, null);
    }

    public ResultVO(Integer code, T data) {
        this(code, null, data);
    }

    public ResultVO(String msg, T data) {
        this(Code.OK.getCode(), msg, data);
    }

    public ResultVO(Integer code, String msg) {
        this(code, msg, null);
    }

    public ResultVO(T data, HttpStatus httpStatus) {
        this(Code.OK.getCode(), Code.OK.getDesc(), data);
        ResponseUtil.setCurrentResponseStatusCode(httpStatus.value());
    }
}
