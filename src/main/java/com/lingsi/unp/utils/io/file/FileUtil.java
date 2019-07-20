package com.lingsi.unp.utils.io.file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 读写文件
 * 1.写文件 A.writeDataToFile 写入文件，可以循环写入　B．clearDataStremIO　关闭文件流
 * 2.读文件　readDataStremToFIle　可以按行循环读出
 */
public class FileUtil {

    private Writer out =null;
    private BufferedReader br = null;
    public static void writeDataToFile(String str,String fileName) throws IOException {
        //文件目录
        Path rootLocation = Paths.get("folder");
        if(Files.notExists(rootLocation)){
            Files.createDirectories(rootLocation);
        }
        //data.js是文件
        Path path = rootLocation.resolve("data.js");
        byte[] strToBytes = str.getBytes();

        Files.write(Files.write(path, strToBytes), strToBytes);
    }

    public  void writeDataStremToFIle(String str,String fileName)  throws IOException{
        File file =new File(FilePath.getFileRootPath()+"/"+fileName);
        if(out == null)
            out =new FileWriter(file,true);
        out.write(str);
    }

    public void clearDataStremIO()throws IOException{
        if(out != null) {
            out.close();
        }
    }

    /**
     * 读入TXT文件
     */
    public  String readDataStremToFIle(String fileName) throws FileNotFoundException{
        String line="";
        String pathname = FilePath.getFileRootPath()+"/"+fileName;
        if(br == null) {
            FileReader reader = new FileReader(pathname);
            br = new BufferedReader(reader);
        }
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * 读入TXT文件
     */
    public  String readDataStremByFileName(String fileName) throws FileNotFoundException{
        String line="";
        if(br == null) {
            FileReader reader = new FileReader(fileName);
            br = new BufferedReader(reader);
        }
        try {
            line = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    /**
     * 将文件转为二进制
     * @param filePath
     * @return
     * @throws IOException
     */
    public static String getByteString(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while((len = fis.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        byte[] fileByte = bos.toByteArray();
        String isoString = new String(fileByte, "ISO-8859-1");
        return isoString;
    }


    public  static void main(String[] args) throws Exception{
        FileUtil fu = new FileUtil();
        /*fu.writeDataStremToFIle("123","lvseng.txt");
        fu.writeDataStremToFIle("\n","lvseng.txt");
        fu.writeDataStremToFIle("456","lvseng.txt");
        fu.clearDataStremIO();*/
        //writeDataToFile("456","folder");

        String fileName="lvseng20190319.txt";
        String line = fu.readDataStremToFIle(fileName);
        System.out.println(line);
        line = fu.readDataStremToFIle(fileName);
        System.out.println(line);
    }
}
