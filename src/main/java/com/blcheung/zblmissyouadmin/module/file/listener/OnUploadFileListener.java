package com.blcheung.zblmissyouadmin.module.file.listener;

import com.blcheung.zblmissyouadmin.module.file.bean.FileEntity;

/**
 * 文件上传监听器
 *
 * @author BLCheung
 * @date 2022/1/15 3:08 上午
 */
public interface OnUploadFileListener {

    /**
     * 每一个文件准备上传前置回调
     *
     * @param file
     * @return java.lang.Boolean
     * @author BLCheung
     * @date 2022/1/16 2:54 上午
     */
    Boolean onEachFilePreUpload(FileEntity file);

    /**
     * 文件上传完成后置回调
     *
     * @param file
     * @author BLCheung
     * @date 2022/1/16 2:55 上午
     */
    void onEachFileUploadAfter(FileEntity file);
}
