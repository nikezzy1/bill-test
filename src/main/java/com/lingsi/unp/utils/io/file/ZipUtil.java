package com.lingsi.unp.utils.io.file;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author xy@lingsi
 */
public final class ZipUtil {
    private static Logger log = LoggerFactory.getLogger("ZipUtil");

    public static boolean compressZipFromFiles(List<String> filePaths, OutputStream outputStream, boolean resolveName) {
        //-- 包装成ZIP格式输出流
        try (ZipOutputStream zipOutStream = new ZipOutputStream(new BufferedOutputStream(outputStream))) {
            // -- 设置压缩方法
            zipOutStream.setMethod(ZipOutputStream.DEFLATED);
            //-- 将多文件循环写入压缩包
            for (String path : filePaths) {
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bis = new BufferedInputStream(fileInputStream);
                String name = file.getName();

                if (resolveName) {
                    int idx = name.indexOf("-");
                    if (idx >= 0) {
                        name = name.substring(idx + 1);
                    }
                }
                //-- 添加ZipEntry，并ZipEntry中写入文件流
                zipOutStream.putNextEntry(new ZipEntry(name));
//                byte[] data = new byte[(int) file.length()];
//                fileInputStream.read(data);
//                zipOutStream.write(data);
                int count;
                byte[] data = new byte[8192];
                while ((count = bis.read(data)) != -1) {
                    zipOutStream.write(data, 0, count);
                }
                bis.close();
                fileInputStream.close();
                zipOutStream.closeEntry();
            }
        } catch (IOException e) {
            log.error("zip file compression failed", e);
            return false;
        }
        return true;
    }

    public static Map<String, String> decompress(String zipFilePath, String dest, String filePrefix) throws Exception {
        File file = new File(zipFilePath);
        if (!file.exists()) {
            throw new  Exception(dest + "所指zip文件不存在");
        }
        if (log.isDebugEnabled()) {
            log.debug("解压 {}", zipFilePath);
        }
        Map<String, String> nameMap = new HashMap<>();
        try {
            ZipFile zf = new ZipFile(file);
            Enumeration<? extends ZipEntry> entries = zf.entries();
            ZipEntry entry;
            while (entries.hasMoreElements()) {
                entry = entries.nextElement();
                if (log.isDebugEnabled()) {
                    log.debug("解压 {}", entry.getName());
                }
                if (entry.isDirectory()) {
                    String dirPath = dest + File.separator + entry.getName();
                    File dir = new File(dirPath);
                    FileUtils.forceMkdir(dir);
                } else {
                    String savedFileName;
                    if (StringUtils.isEmpty(filePrefix)) {
                        savedFileName = entry.getName();
                    } else {
                        savedFileName = filePrefix + entry.getName();
                    }
                    nameMap.put(entry.getName(),  savedFileName);
                    File f = new File(dest + File.separator + savedFileName);
                    if (!f.exists()) {
                        File parentDir = f.getParentFile();
                        FileUtils.forceMkdir(parentDir);
                    }
                    f.createNewFile();
                    InputStream is = zf.getInputStream(entry);
                    FileOutputStream fos = new FileOutputStream(f);

                    int count;
                    byte[] buf = new byte[8192];
                    while ((count = is.read(buf)) != -1) {
                        fos.write(buf, 0, count);
                    }
                    is.close();
                    fos.close();
                }
            }
        } catch (IOException e) {
            log.error("zip file decompression failed. zipFilePath: {}, dest: {}, filePrefix: {}", zipFilePath, dest, filePrefix, e);
            throw new  Exception("zip file decompression failed", e);
        }
        return nameMap;
    }
}
