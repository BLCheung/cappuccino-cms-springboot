package com.blcheung.zblmissyouadmin.common.bean;

import com.blcheung.zblmissyouadmin.common.enumeration.UserLevel;
import lombok.*;

/**
 * 路由信息实体对象
 *
 * @author BLCheung
 * @date 2022/1/3 9:13 下午
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RouterInfo {
    // 路由唯一标识
    private String identity;

    // 路由模块
    private String module;

    // 路由名称
    private String router;

    // 该路由的权限级别
    private UserLevel level;
}
