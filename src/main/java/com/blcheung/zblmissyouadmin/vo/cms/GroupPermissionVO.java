package com.blcheung.zblmissyouadmin.vo.cms;

import com.blcheung.zblmissyouadmin.kit.BeanKit;
import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
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

    private List<PermissionVO> permissions;

    public GroupPermissionVO(List<CmsPermissionDO> cmsPermissions) {
        this.permissions = BeanKit.transformList(cmsPermissions, new PermissionVO());
    }
}
