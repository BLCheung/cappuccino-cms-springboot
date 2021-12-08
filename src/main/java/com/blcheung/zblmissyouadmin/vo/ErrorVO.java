package com.blcheung.zblmissyouadmin.vo;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.interfaces.ResultAction;
import com.blcheung.zblmissyouadmin.util.RequestUtil;
import com.blcheung.zblmissyouadmin.util.ResponseUtil;
import org.springframework.http.HttpStatus;

/**
 * @author BLCheung
 * @date 2021/12/8 9:34 下午
 */
public class ErrorVO extends ResultVO<Object> implements ResultAction {
    private String request;

    public ErrorVO() {
        this(Code.INTERNAL_SERVER_ERROR.getCode(), Code.INTERNAL_SERVER_ERROR.getDesc());
    }

    public ErrorVO(Object data) {
        this();
        this.setData(data);
    }

    public ErrorVO(Integer code, String msg) {
        this(code, msg, null);
    }

    public ErrorVO(Integer code, Object data) {
        this(code, null, data);
    }

    public ErrorVO(Integer code, String msg, Object data) {
        super(code, msg, data);
        this.setRequest();
        ResponseUtil.setCurrentResponseStatusCode(HttpStatus.OK.value());
    }

    @Override
    public String getRequest() {
        return this.request;
    }

    @Override
    public void setRequest() {
        this.request = RequestUtil.getActionRequest();
    }

    @Override
    public void setRequest(String request) {
        this.request = request;
    }
}
