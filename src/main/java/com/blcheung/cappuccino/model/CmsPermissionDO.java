package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 *
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
@TableName("cms_permission")
public class CmsPermissionDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 权限名称，例如：访问首页
     */
    private String name;

    /**
     * 权限所属模块，例如：人员管理
     */
    private String module;

    /**
     * 0：关闭 1：开启
     */
    private Boolean mount;


}
