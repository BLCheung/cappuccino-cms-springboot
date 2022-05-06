package com.blcheung.cappuccino.vo.cms;

import lombok.Getter;
import lombok.Setter;

/**
 * @author BLCheung
 * @date 2022/1/18 2:15 上午
 */
@Getter
@Setter
public class FileVO {

    /**
     * 文件id
     */
    private Long id;

    /**
     * 后端分配的文件名称
     */
    private String name;

    /**
     * 文件真实url
     */
    private String url;

    /**
     * 若为本地上传类型，则为文件本地路径，否则为服务器存放资源路径
     */
    private String path;

    /**
     * 文件大小
     */
    private Integer size;
}
