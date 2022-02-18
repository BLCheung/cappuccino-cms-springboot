package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-18
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("sku_spec")
public class SkuSpecDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -3784284455703794187L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private Long skuId;

    private Long keyId;

    private Long valueId;

}
