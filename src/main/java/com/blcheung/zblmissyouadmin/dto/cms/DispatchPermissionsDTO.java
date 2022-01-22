package com.blcheung.zblmissyouadmin.dto.cms;

import com.blcheung.zblmissyouadmin.validator.LongList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/22 2:27 上午
 */
@Getter
@Setter
public class DispatchPermissionsDTO {

    @Positive
    @NotNull(message = "{group.id.not_null}")
    private Long groupId;

    @LongList(message = "{permission.ids_long_list}", allowBlank = true)
    private List<Long> permissionIds;
}
