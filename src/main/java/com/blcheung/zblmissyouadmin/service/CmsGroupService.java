package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.common.enumeration.GroupLevel;
import com.blcheung.zblmissyouadmin.model.CmsGroupDO;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
public interface CmsGroupService extends IService<CmsGroupDO> {

    /**
     * 创建分组
     *
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 12:37 上午
     */
    boolean createGroup(CmsGroupDO groupDO);

    /**
     * 通过id检查分组是否存在
     *
     * @param id
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 12:33 上午
     */
    boolean checkGroupExistById(Long id);

    /**
     * 通过名字检查分组是否存在
     *
     * @param name
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 12:33 上午
     */
    boolean checkGroupExistByName(String name);

    /**
     * 通过分组级别枚举获取分组id
     *
     * @param groupLevel
     * @return java.lang.Long
     * @author BLCheung
     * @date 2021/12/21 12:53 上午
     */
    Long getGroupIdByEnum(GroupLevel groupLevel);
}
