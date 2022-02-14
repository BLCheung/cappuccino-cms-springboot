package com.blcheung.zblmissyouadmin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.zblmissyouadmin.mapper.SpecKeyMapper;
import com.blcheung.zblmissyouadmin.model.SpecKeyDO;
import com.blcheung.zblmissyouadmin.service.SpecKeyService;
import org.springframework.stereotype.Service;

/**
 * @author BLCheung
 * @date 2022/2/14 12:44 上午
 */
@Service
public class SpecKeyServiceImpl extends ServiceImpl<SpecKeyMapper, SpecKeyDO> implements SpecKeyService {}
