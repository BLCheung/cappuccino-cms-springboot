package com.blcheung.cappuccino.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author BLCheung
 * @date 2022/2/10 7:51 下午
 */
@Getter
@Setter
public class SpuDetailBO {

    private Long id;

    private String categoryName;

    private String sketchSpecName;

    private String defaultSkuTitle;

    private List<String> spuKeys;

    // private List<String> spuTags;

    private List<String> spuImages;

    private List<String> spuDetailImages;
}
