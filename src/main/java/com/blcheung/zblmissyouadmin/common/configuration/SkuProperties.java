package com.blcheung.zblmissyouadmin.common.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 针对SKU相关的配置属性文件
 *
 * @author BLCheung
 * @date 2022/2/18 8:56 下午
 */
@ConfigurationProperties(prefix = "missyou.sku")
@Component
@Getter
@Setter
public class SkuProperties {

    /**
     * Sku最大购买数量
     */
    private Integer maxBuyCount;
}
