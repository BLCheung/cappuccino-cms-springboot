package com.blcheung.zblmissyouadmin.module.file;

import com.blcheung.zblmissyouadmin.module.file.common.AbstractUploader;
import com.blcheung.zblmissyouadmin.module.file.common.UploadType;
import com.blcheung.zblmissyouadmin.module.file.configuration.CmsFileProperties;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.ByteArrayInputStream;

/**
 * @author BLCheung
 * @date 2022/1/19 11:38 下午
 */
@Slf4j
public class QiniuUploader extends AbstractUploader {

    @Autowired
    private CmsFileProperties cmsFileProperties;

    @Value("${qiniu.access-key}")
    private String accessKey;

    @Value("${qiniu.secret-key}")
    private String secretKey;

    @Value("${qiniu.bucket}")
    private String bucket;

    private UploadManager uploadManager;

    private String uploadToken;

    public void initQiniuUpload() {
        Configuration configuration = new Configuration(Region.huanan());
        uploadManager = new UploadManager(configuration);
        Auth auth = Auth.create(this.accessKey, this.secretKey);
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody",
                      "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        this.uploadToken = auth.uploadToken(this.bucket, null, 3600, stringMap);
    }

    @Override
    protected Boolean onUploadFile(byte[] bytes, String filePath, String fileName) {
        this.initQiniuUpload();
        ByteArrayInputStream bas = new ByteArrayInputStream(bytes);
        try {
            Response response = this.uploadManager.put(bas, fileName, this.uploadToken, null, null);
            System.out.println(response.bodyString());
            return response.isOK();
        } catch (QiniuException e) {
            e.printStackTrace();
            log.error(fileName + "上传到qiniu云失败");
            return false;
        }
    }

    @Override
    protected CmsFileProperties getFileProperties() {
        return this.cmsFileProperties;
    }

    @Override
    protected String getFileUrl(String filePath) {
        String domain = this.getFileProperties()
                            .getDomain();
        return domain + filePath;
    }

    @Override
    protected String getFileType() {
        return UploadType.REMOTE;
    }

    @Override
    protected String getStorePath(String newFileName) {
        String serverPath = this.getFileProperties()
                                .getServerPath();

        return serverPath + newFileName;
    }
}
