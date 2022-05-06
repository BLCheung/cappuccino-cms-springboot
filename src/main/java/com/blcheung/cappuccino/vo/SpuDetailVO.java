package com.blcheung.cappuccino.vo;

import com.blcheung.cappuccino.bo.SpuDetailBO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/10 3:21 上午
 */
@Getter
@Setter
public class SpuDetailVO extends SpuVO {

    private String categoryName;

    private String sketchSpecName;

    private String defaultSkuTitle;

    private List<String> spuKeys;

    // private List<String> spuTags;

    private List<String> spuImages;

    private List<String> spuDetailImages;

    public SpuDetailVO(SpuDetailBO bo) {
        BeanUtils.copyProperties(bo, this);
    }
}
