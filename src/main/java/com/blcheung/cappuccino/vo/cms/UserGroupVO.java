package com.blcheung.cappuccino.vo.cms;

import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.model.CmsGroupDO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/26 2:26 上午
 */
@Getter
@Setter
public class UserGroupVO extends UserVO {

    private List<GroupVO> groups;

    public UserGroupVO(List<CmsGroupDO> cmsGroups) {
        this.groups = BeanKit.transformList(cmsGroups, GroupVO.class);
    }
}
