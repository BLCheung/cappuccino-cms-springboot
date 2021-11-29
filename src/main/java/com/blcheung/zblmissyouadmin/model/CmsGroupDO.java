package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

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
@TableName("cms_group")
public class CmsGroupDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    /**
     * 分组名称，例如：搬砖者
     */
    private String name;

    /**
     * 分组信息：例如：搬砖的人
     */
    private String info;

    /**
     * 分组级别 1：root 2：guest 3：user  root（root、guest分组只能存在一个)
     */
    private Integer level;


}
