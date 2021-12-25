package com.blcheung.zblmissyouadmin.service;

import com.blcheung.zblmissyouadmin.dto.NewGroupDTO;

/**
 * @author BLCheung
 * @date 2021/12/21 9:45 下午
 */
public interface CmsAdminService {

    /**
     * 新建分组
     *
     * @param dto
     * @return boolean
     * @author BLCheung
     * @date 2021/12/21 9:47 下午
     */
    boolean createGroup(NewGroupDTO dto);

    /**
     * 删除分组
     *
     * @param groupId
     * @return boolean
     * @author BLCheung
     * @date 2021/12/26 4:03 上午
     */
    boolean deleteGroup(Long groupId);
}
