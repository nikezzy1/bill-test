package com.lingsi.unp.utils.io.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;


public final class MultiFileUtils {

    private static Logger log = LoggerFactory.getLogger(MultiFileUtils.class);

    public static ResponseEntity<InputStreamResource> download(String filePath,String fileName) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
        ResponseEntity<InputStreamResource> response = null;
        try {
            File file = new File(filePath+fileName);
            InputStream fileInputStream = new FileInputStream(file);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", fileName));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            response = ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("application/octet-stream;charset=utf-8")).body(new InputStreamResource(fileInputStream));
        } catch (FileNotFoundException e1) {
            log.error("找不到指定的文件", e1);
        } catch (IOException e) {
            log.error("获取不到文件流", e);
        } catch (Exception e) {
            log.error("下载失败", e);
        }
        return response;
    }


    public static File multipartFileToFile(MultipartFile file, String newFilePath) throws Exception {
        if (file == null) {
            return null;
        }
        try (InputStream ins = file.getInputStream()) {
            File toFile = new File(newFilePath);
            inputStreamToFile(ins, toFile);
            return toFile;
        }
    }

    public static void inputStreamToFile(InputStream ins, File file) throws Exception {
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        byte[] buffer = new byte[8192];
        while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush();
        os.close();
        ins.close();
    }

}
