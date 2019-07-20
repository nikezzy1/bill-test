package com.lingsi.unp.utils.io.file;


import com.lingsi.unp.service.notarial.myenum.MyEnum;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;

public class FilePath {
//    private static final String root="./folder";

    private static final String root="/home/folder";

//    private static final String root="G:\\version\\unp\\0430\\unp\\folder";

    public static String getFileRootPath(){
        return root;
    }
    public static String getTemplateFileName(MyEnum myEnum){
        return getFileRootPath()+myEnum.getValue();

    }
    public static String getTemplateFilePath(MyEnum myEnum){
        return getFileRootPath()+myEnum.getValue();
    }

    public static String getFileName(){
        String fileName=System.currentTimeMillis()+ ""+new Random().nextInt(99999999);
        return fileName;
    }

    /**
     * 获取文件名后缀
     * @param fname
     * @return
     */
    public static String getpostfix (String fname){
        String postfix=null;
        if (fname==null) {
            return "";
        }
        if(fname.indexOf(".")!=-1){
            postfix=fname.substring(fname.lastIndexOf("."));
        }else{
            return "非法文件名";
        }
        return postfix;
    }

    public static  String getRandomCode(int length){
        String string = "abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt((int) Math.round(Math.random() * (len-1))));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        System.out.println(getpostfix("123....jpg"));
    }

}
