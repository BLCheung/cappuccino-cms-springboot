package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.dto.RegisterUserDTO;
import com.blcheung.zblmissyouadmin.mapper.CmsUserMapper;
import com.blcheung.zblmissyouadmin.model.CmsUserDO;
import com.blcheung.zblmissyouadmin.service.CmsUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author BLCheung
 * @since 2021-11-28
 */
@Service
public class CmsUserServiceImpl extends ServiceImpl<CmsUserMapper, CmsUserDO> implements CmsUserService {

}
