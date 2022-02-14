package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@TableName("spu_img")
public class SpuImgDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String img;

    private Long spuId;

}
