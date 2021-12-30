package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * cms用户实体
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
@TableName("cms_user")
public class CmsUserDO extends BaseDO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3956171190208177861L;

    /**
     * 用户名，唯一
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;


}
