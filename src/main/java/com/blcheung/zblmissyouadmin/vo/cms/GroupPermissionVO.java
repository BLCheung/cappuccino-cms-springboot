package com.blcheung.zblmissyouadmin.vo.cms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/21 10:56 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class GroupPermissionVO extends GroupVO {

    private List<PermissionModuleVO> modules;

    public GroupPermissionVO(List<PermissionModuleVO> modules) {
        this.modules = modules;
    }
}
