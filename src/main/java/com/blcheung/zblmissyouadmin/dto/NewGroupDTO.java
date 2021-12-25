package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.validator.LongList;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author BLCheung
 * @date 2021/12/21 9:15 下午
 */
@Getter
@Setter
public class NewGroupDTO {

    /**
     * 分组名称
     *
     * @author BLCheung
     * @date 2021/12/21 9:24 下午
     * @param null
     * @return null
     */
    @NotBlank(message = "{group.name.not_blank}")
    @Length(min = 1, max = 60, message = "{group.name.length}")
    private String name;

    /**
     * 分组描述
     *
     * @author BLCheung
     * @date 2021/12/21 9:24 下午
     * @param null
     * @return null
     */
    @Length(max = 255, message = "{group.info.length}")
    private String info;

    /**
     * 分配的权限id集合
     *
     * @author BLCheung
     * @date 2021/12/21 9:25 下午
     * @param null
     * @return null
     */
    @LongList(allowBlank = true)
    private List<Long> permissionIds;
}
