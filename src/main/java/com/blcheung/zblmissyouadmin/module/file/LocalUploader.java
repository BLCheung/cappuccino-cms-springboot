package com.blcheung.zblmissyouadmin.module.file;

import com.blcheung.zblmissyouadmin.module.file.common.AbstractUploader;
import com.blcheung.zblmissyouadmin.module.file.common.UploadType;
import com.blcheung.zblmissyouadmin.module.file.configuration.CmsFileProperties;
import com.blcheung.zblmissyouadmin.module.file.kit.FileKit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author BLCheung
 * @date 2022/1/17 11:42 下午
 */
@Slf4j
public class LocalUploader extends AbstractUploader {

    @Autowired
    private CmsFileProperties cmsFileProperties;

    @PostConstruct
    public void initLocalPath() {
        FileKit.initStorePath(this.getFileProperties()
                                  .getLocalPath());
    }

    @Override
    protected Boolean onUploadFile(byte[] bytes, String filePath, String fileName) {
        try {
            BufferedOutputStream bfs = new BufferedOutputStream(new FileOutputStream(filePath));
            bfs.write(bytes);
            bfs.close();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("本地上传onUploadFile err " + e);
            return false;
        }

        return true;
    }

    @Override
    protected CmsFileProperties getFileProperties() {
        return this.cmsFileProperties;
    }

    @Override
    protected String getFileUrl(String filePath) {
        String domain = this.getFileProperties()
                            .getDomain();

        if (System.getProperty("os.name")
                  .toUpperCase()
                  .contains("WINDOWS")) {
            // 将window平台下的 \ 替换成 /
            return domain + filePath.replaceAll("\\\\", "/");
        } else {
            return domain + filePath;
        }
    }

    @Override
    protected String getFileType() {
        return UploadType.LOCAL;
    }

    @Override
    protected String getStorePath(String newFileName) {
        String localPath = this.getFileProperties()
                               .getLocalPath();
        Date now = new Date();
        String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(now);

        String staticDirPath = Path.of(localPath, dateDir)
                                   .toAbsolutePath()
                                   .toString();
        // 初始化资源目录
        FileKit.initStorePath(staticDirPath);

        return Paths.get(localPath, dateDir, newFileName)
                    .toString();
    }
}
