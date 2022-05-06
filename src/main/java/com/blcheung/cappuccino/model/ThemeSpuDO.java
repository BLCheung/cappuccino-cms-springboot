package com.blcheung.cappuccino.model;

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
 * @since 2022-02-19
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("theme_spu")
public class ThemeSpuDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -5079129913301589989L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long themeId;

    private Long spuId;

}
