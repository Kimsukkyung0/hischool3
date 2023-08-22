package com.green.secondproject.common.utils;

import java.io.File;
import java.nio.file.Paths;
import java.util.UUID;

public class MyFileUtils {
    public static String getExt(String fileNm) {
        return fileNm.substring(fileNm.lastIndexOf("."));
    }

    public static String getFileNm(String fileNm) {
        return fileNm.substring(0, fileNm.lastIndexOf("."));
    }

    public static String makeRandomFileNm(String fileNm) {
        return UUID.randomUUID() + getExt(fileNm);
    }

    public static String getAbsolutePath(String src) {
        return Paths.get(src).toFile().getAbsolutePath();
    }

    public static void delFolder(String path) {
        File file = new File(path);
        if (file.exists() && file.isDirectory()) {
            File[] fileArr = file.listFiles();
            for (File f : fileArr) {
                if (f.isDirectory()) {
                    delFolder(f.getPath());
                } else {
                    f.delete();
                }
            }
        }
        file.delete();
    }
}
