package com.blcheung.cappuccino.vo.cms;

import com.blcheung.cappuccino.kit.BeanKit;
import com.blcheung.cappuccino.model.CmsGroupDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/3/3 11:06 下午
 */
@Getter
@Setter
@NoArgsConstructor
public class UserInfoVO extends UserVO {

    private List<GroupVO> groups;

    public UserInfoVO(List<CmsGroupDO> groups) {
        this.groups = BeanKit.transformList(groups, GroupVO.class);
    }
}
