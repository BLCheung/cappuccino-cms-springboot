package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.blcheung.cappuccino.common.enumeration.UserIdentifyType;
import lombok.*;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("cms_user_identity")
public class CmsUserIdentityDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户加密类型
     */
    private UserIdentifyType identityType;

    private String identifier;

    private String credential;


}
