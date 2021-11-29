package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("cms_group_permission")
public class CmsGroupPermissionDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 分组id
     */
    private Integer groupId;

    /**
     * 权限id
     */
    private Integer permissionId;


}
