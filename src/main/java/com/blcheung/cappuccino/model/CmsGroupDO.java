package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blcheung.cappuccino.common.enumeration.GroupLevel;
import lombok.*;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
     * 分组级别 1：root 2：admin 3：guest 4: user
     */
    @TableField(value = "`level`")
    private GroupLevel level;


}
