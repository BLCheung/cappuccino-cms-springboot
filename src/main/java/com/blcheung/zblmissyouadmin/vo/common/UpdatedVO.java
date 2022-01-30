package com.blcheung.zblmissyouadmin.vo.common;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.interfaces.ResultAction;
import com.blcheung.zblmissyouadmin.util.RequestUtil;
import com.blcheung.zblmissyouadmin.util.ResponseUtil;
import org.springframework.http.HttpStatus;

/**
 * @author BLCheung
 * @date 2021/12/2 12:36 上午
 */
public class UpdatedVO extends ResultVO<Object> implements ResultAction {
    private String request;

    public UpdatedVO() {
        this(Code.UPDATED.getCode(), Code.UPDATED.getDesc());
    }

    public UpdatedVO(Object data) {
        this();
        this.setData(data);
    }

    public UpdatedVO(Integer code, String msg) {
        this(code, msg, null);
    }

    public UpdatedVO(Integer code, Object data) {
        this(code, null, data);
    }

    public UpdatedVO(Integer code, String msg, Object data) {
        super(code, msg, data);
        this.setRequest();
        ResponseUtil.setCurrentResponseStatusCode(HttpStatus.OK.value());
    }

    @Override
    public String getRequest() {
        return this.request;
    }

    @Override
    public void setRequest(String request) {
        this.request = request;
    }

    @Override
    public void setRequest() {
        this.request = RequestUtil.getActionRequest();
    }
}
