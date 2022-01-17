package com.blcheung.zblmissyouadmin.module.file.kit;

import org.springframework.boot.system.SystemProperties;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.util.unit.DataSize;

import java.io.File;
import java.nio.file.Path;

/**
 * 文件相关辅助类
 *
 * @author BLCheung
 * @date 2022/1/15 2:24 上午
 */
public class FileKit {

    public static Boolean isAbsolutePath(String path) {
        return Path.of(path)
                   .isAbsolute();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void initStorePath(String storeDir) {
        String storePath;
        if (FileKit.isAbsolutePath(storeDir)) {
            storePath = storeDir;
        } else {
            Path absolutePath = Path.of(FileKit.getProjectDirectory(), storeDir)
                                    .toAbsolutePath();
            storePath = absolutePath.toString();
        }

        File file = Path.of(storePath)
                        .toFile();
        if (!file.exists()) file.mkdirs();
    }

    public static String getFileAbsolutePath(String dirName, String fileName) {
        if (FileKit.isAbsolutePath(dirName)) {
            return Path.of(dirName, fileName)
                       .toAbsolutePath()
                       .toString();
        } else {
            return Path.of(FileKit.getProjectDirectory(), dirName, fileName)
                       .toAbsolutePath()
                       .toString();
        }
    }

    public static String getProjectDirectory() {
        // 如果当前项目目录为D:/project/test/ 则返回D:/project/test/
        return System.getProperty("user.dir");
    }

    public static String getFileExt(String fileName) {
        if (!StringUtils.hasText(fileName)) return "";
        int index = fileName.lastIndexOf(".");
        return fileName.substring(index);
    }

    public static Long parseSize(String size) {
        DataSize parse = DataSize.parse(size);
        return parse.toBytes();
    }

    public static String getFileMD5(byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }
}
