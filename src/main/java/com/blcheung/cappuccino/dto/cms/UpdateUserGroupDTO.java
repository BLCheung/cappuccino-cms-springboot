package com.blcheung.cappuccino.dto.cms;

import com.blcheung.cappuccino.validator.LongList;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author BLCheung
 * @date 2022/1/25 4:12 上午
 */
@Getter
@Setter
public class UpdateUserGroupDTO {

    @NotNull(message = "{group.id.not_null}")
    @LongList(message = "{group.ids.long_list}")
    private List<Long> groupIds;
}
