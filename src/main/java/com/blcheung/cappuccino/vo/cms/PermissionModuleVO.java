package com.blcheung.cappuccino.vo.cms;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/3/3 10:34 下午
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermissionModuleVO {

    private String module;

    private List<PermissionVO> permissions;
}
