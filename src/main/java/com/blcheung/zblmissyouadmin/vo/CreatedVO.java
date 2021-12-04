package com.blcheung.zblmissyouadmin.vo;

import com.blcheung.zblmissyouadmin.common.Code;
import com.blcheung.zblmissyouadmin.common.interfaces.ResultAction;
import com.blcheung.zblmissyouadmin.util.RequestUtil;
import com.blcheung.zblmissyouadmin.util.ResponseUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.http.HttpStatus;

/**
 * @author BLCheung
 * @date 2021/12/2 12:36 上午
 */
public class CreatedVO extends ResultVO<Object> implements ResultAction {
    private String request;

    public CreatedVO() {
        this(Code.CREATED.getCode(), Code.CREATED.getDesc());
    }

    public CreatedVO(Object data) {
        this();
        this.setData(data);
    }

    public CreatedVO(Integer code, String msg) {
        this(code, msg, null);
    }

    public CreatedVO(Integer code, Object data) {
        this(code, null, data);
    }

    public CreatedVO(Integer code, String msg, Object data) {
        super(code, msg, data);
        this.setRequest();
        ResponseUtil.setCurrentResponseStatusCode(HttpStatus.CREATED.value());
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
