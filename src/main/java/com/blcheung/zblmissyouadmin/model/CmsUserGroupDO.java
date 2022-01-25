package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;


/**
 * <p>
 * cms用户组实体
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
@TableName("cms_user_group")
public class CmsUserGroupDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7812857663526275266L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 分组id
     */
    private Long groupId;

    public CmsUserGroupDO(Long userId, Long groupId) {
        this.userId  = userId;
        this.groupId = groupId;
    }
}
