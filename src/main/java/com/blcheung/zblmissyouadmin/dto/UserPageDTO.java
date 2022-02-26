package com.blcheung.zblmissyouadmin.dto;

import com.blcheung.zblmissyouadmin.dto.common.BasePagingDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/2/26 5:07 下午
 */
@Getter
@Setter
public class UserPageDTO extends BasePagingDTO {

    private String keyword;
}
