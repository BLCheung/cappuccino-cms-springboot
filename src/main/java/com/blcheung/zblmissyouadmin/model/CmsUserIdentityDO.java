package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * cms用户账号类型
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Getter
@Setter
@TableName("cms_user_identity")
public class CmsUserIdentityDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Integer userId;

    private String identityType;

    private String identifier;

    private String credential;


}
