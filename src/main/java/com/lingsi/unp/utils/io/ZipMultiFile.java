package com.lingsi.unp.utils.io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @describe 压缩多个文件
 * @author zfc
 * @date 2018年1月11日 下午8:34:00
 */
public class ZipMultiFile {

    public static void main(String[] args) {
        File[] srcFiles = { new File("D:/TSBrowserDownloads/155646213785482370891..jpg"),
                new File("D:/TSBrowserDownloads/155646263440326327467..jpg"),
                new File("D:/test/155652601837597038747.pdf") };

        Map newFileNameMap = new HashMap();
        newFileNameMap.put("155646213785482370891..jpg", "是.jpg");
        newFileNameMap.put("155646263440326327467..jpg", "你.jpg");
        newFileNameMap.put("155652601837597038747.pdf", "吗.pdf");
        File zipFile = new File("D:/test/sd.zip");
        // 调用压缩方法
        zipFiles(srcFiles, zipFile, newFileNameMap);
    }

    public static void zipFiles(File[] srcFiles, File zipFile, Map newFileNameMap) {
        Logger logger = LogManager.getLogger();
        logger.info("**************** zip in ****************");
        // 判断压缩后的文件存在不，不存在则创建
        if (!zipFile.exists()) {
            try {
                zipFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 创建 FileOutputStream 对象
        FileOutputStream fileOutputStream = null;
        // 创建 ZipOutputStream
        ZipOutputStream zipOutputStream = null;
        // 创建 FileInputStream 对象
        FileInputStream fileInputStream = null;

        try {
            // 实例化 FileOutputStream 对象
            fileOutputStream = new FileOutputStream(zipFile);
            // 实例化 ZipOutputStream 对象
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            // 创建 ZipEntry 对象
            ZipEntry zipEntry = null;
            // 遍历源文件数组
            for (int i = 0; i < srcFiles.length; i++) {
                logger.info("**************** [" + i + "] ****************");
                // 将源文件数组中的当前文件读入 FileInputStream 流中
                fileInputStream = new FileInputStream(srcFiles[i]);
                // 实例化 ZipEntry 对象，源文件数组中的当前文件
                String fileName = srcFiles[i].getName();
                zipEntry = new ZipEntry(newFileNameMap.get(fileName).toString() + fileName.substring(fileName.lastIndexOf(".")));
                zipOutputStream.putNextEntry(zipEntry);
                // 该变量记录每次真正读的字节个数
                int len;
                // 定义每次读取的字节数组
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer)) > 0) {
                    zipOutputStream.write(buffer, 0, len);
                }
                logger.info("**************** [" + i + "] ****************");

            }
            zipOutputStream.flush();
            zipOutputStream.closeEntry();
            zipOutputStream.close();
            fileInputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("**************** zip out ****************");

    }
}