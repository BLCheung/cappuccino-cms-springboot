package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.blcheung.zblmissyouadmin.model.BaseDO;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-30
 */
@Getter
@Setter
@TableName("banner")
public class BannerDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String name;

    private String description;

    private String title;

    /**
     * 部分banner可能有标题图片
     */
    private String img;


}
