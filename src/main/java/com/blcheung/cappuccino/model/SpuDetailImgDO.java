package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-02-10
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("spu_detail_img")
public class SpuDetailImgDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String img;

    private Long spuId;

    @TableField(value = "`index`")
    private Integer index;

}
