package com.lingsi.unp.utils.pdf;

import com.lingsi.framework.htmltopdf.HtmlToPdf;
import com.lingsi.framework.htmltopdf.HtmlToPdfObject;
import com.lingsi.framework.htmltopdf.wkhtmltopdf.WkHtmlToPdf;
import com.lingsi.unp.service.notarial.myenum.MyEnum;
import com.lingsi.unp.utils.io.file.FilePath;
import com.lingsi.unp.utils.io.file.FileUtil;
/*import io.woo.htmltopdf.HtmlToPdf;
import io.woo.htmltopdf.HtmlToPdfObject;*/


public class PdfUtil {
    public static  void main(String[] args) throws Exception {
        String htmlTemplate="<html>" +
                "<head>\n" +
                "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "  <title>公证书模板</title>\n" +
                "</head>\n" +
                "<body>" +
                "<p><br/></p>\n" +
                "\t<p style=\"text-align:center;line-height:19px;text-autospace:none\">\n" +
                "\t    <span style=\"font-size: 16px\">公证书</span>\n" +
                "\t</p>\n" +
                "\t<p style=\"text-align:right;line-height:19px;text-autospace:none\">\n" +
                "\t    <span style=\"font-size: 16px\">（</span><span style=\"font-size: 16px;font-family: Times-Roman, serif\">xxxx</span><span style=\"font-size: 16px\">）浙衢信证互字第</span><span style=\"font-size: 16px;font-family: Times-Roman, serif\">xx</span><span style=\"font-size: 16px\">号</span>\n" +
                "\t</p>\n" +
                "\t<p style=\"line-height: 19px\">\n" +
                "\t    <span style=\"font-size: 16px\">申请人：</span><span style=\"font-size: 16px;font-family: Times-Roman, serif\"> </span>\n" +
                "\t</p>\n" +
                "\t<p style=\"line-height: 19px\">\n" +
                "\t    <span style=\"font-size: 16px\">单位名称：</span><span style=\"font-size: 16px;font-family: Times-Roman, serif\"> &nbsp;<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></span><span style=\"font-size: 16px\">统一机构代码证号：</span><span style=\"font-size: 16px;font-family: Times-Roman, serif\"> </span>\n" +
                "\t</p>\n" +
                "</body>" +
                "</html>";

        boolean success=false;
        try {
            success = HtmlToPdf.create()
                    .object(HtmlToPdfObject.forHtml(htmlTemplate))
                    .convert("D:/suntao.pdf");

        }catch (Exception e){
            e.printStackTrace();
        }catch (Error e){
            e.printStackTrace();
        }

    }



}
