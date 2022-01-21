package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.cms.NewAdminGroupDTO;

/**
 * @author BLCheung
 * @date 2021/12/26 10:02 下午
 */
public interface CmsRootService {

    /**
     * 新建管理员分组
     *
     * @param dto
     * @return boolean
     * @author BLCheung
     * @date 2021/12/26 10:01 下午
     */
    boolean createAdminGroup(NewAdminGroupDTO dto);

    /**
     * 删除管理员分组
     *
     * @param groupId
     * @return boolean
     * @author BLCheung
     * @date 2021/12/26 10:01 下午
     */
    boolean deleteAdminGroup(Long adminGroupId);
}
