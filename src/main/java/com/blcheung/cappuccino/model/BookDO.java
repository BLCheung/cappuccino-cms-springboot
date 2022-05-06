package com.blcheung.cappuccino.model;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author BLCheung
 * @since 2022-01-22
 */
@Getter
@Setter
@NoArgsConstructor
@TableName("book")
public class BookDO extends BaseDO {

    private static final long serialVersionUID = 1L;

    private String title;

    private String author;

    private String summary;

    private String image;


}
