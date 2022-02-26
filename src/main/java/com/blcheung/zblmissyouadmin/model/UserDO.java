package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-26
 */
@Getter
@Setter
@TableName("user")
public class UserDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String openid;

    private String nickname;

    private Long unifyUid;

    private String email;

    private String password;

    private String mobile;

    private String wxProfile;

}
