package com.blcheung.cappuccino.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blcheung.cappuccino.mapper.SpuImgMapper;
import com.blcheung.cappuccino.model.SpuImgDO;
import com.blcheung.cappuccino.service.SpuImgService;
import org.springframework.stereotype.Service;

/**
 * @author BLCheung
 * @date 2022/2/14 12:50 上午
 */
@Service
public class SpuImgServiceImpl extends ServiceImpl<SpuImgMapper, SpuImgDO> implements SpuImgService {}
