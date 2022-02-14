package com.blcheung.zblmissyouadmin.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @since 2022-02-14
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("spu_key")
public class SpuKeyDO implements Serializable {

    @Serial
    private static final long serialVersionUID = -6572093631940785848L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long spuId;

    private Long specKeyId;

}
