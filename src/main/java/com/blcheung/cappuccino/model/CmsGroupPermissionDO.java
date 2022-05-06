package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * cms用户组权限实体
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Getter
@Setter
@AllArgsConstructor
@TableName("cms_group_permission")
public class CmsGroupPermissionDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Long groupId;

    /**
     * 权限id
     */
    private Long permissionId;


}
