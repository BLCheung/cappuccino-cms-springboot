package com.blcheung.cappuccino.dto.cms;

import com.blcheung.cappuccino.dto.common.BasePagingDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

/**
 * @author BLCheung
 * @date 2022/1/23 10:01 下午
 */
@Getter
@Setter
public class QueryUsersDTO extends BasePagingDTO {

    @Positive
    private Long groupId;
}
