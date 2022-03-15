package com.blcheung.zblmissyouadmin.vo.cms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/3/3 9:41 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class UserPermissionsVO extends UserVO {

    private Integer userLevel;

    private List<PermissionModuleVO> modules;

    public UserPermissionsVO(List<PermissionModuleVO> modules) {
        this.modules = modules;
    }
}
