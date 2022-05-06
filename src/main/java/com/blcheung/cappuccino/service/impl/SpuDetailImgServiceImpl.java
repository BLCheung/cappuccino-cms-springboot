package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.mapper.SpuDetailImgMapper;
import com.blcheung.cappuccino.model.SpuDetailImgDO;
import com.blcheung.cappuccino.service.SpuDetailImgService;
import org.springframework.stereotype.Service;

/**
 * @author BLCheung
 * @date 2022/2/14 12:48 上午
 */
@Service
public class SpuDetailImgServiceImpl extends ServiceImpl<SpuDetailImgMapper, SpuDetailImgDO>
        implements SpuDetailImgService {}
