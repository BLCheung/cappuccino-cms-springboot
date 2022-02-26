package com.blcheung.zblmissyouadmin.vo;

import com.blcheung.zblmissyouadmin.util.JSONConverterUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author BLCheung
 * @date 2022/2/26 5:29 下午
 */
@Getter
@Setter
public class UserVO {

    private Long id;

    private String openid;

    private String nickname;

    private Long unifyUid;

    private String email;

    private String password;

    private String mobile;

    private Map<String, Object> wxProfile;

    public void setWxProfile(String wxProfile) {
        this.wxProfile = JSONConverterUtil.convertJSONToObject(wxProfile, new TypeReference<Map<String, Object>>() {});
    }
}
