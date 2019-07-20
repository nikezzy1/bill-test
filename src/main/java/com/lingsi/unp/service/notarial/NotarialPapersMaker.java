package com.lingsi.unp.service.notarial;

import com.alibaba.fastjson.JSON;
import com.lingsi.framework.htmltopdf.HtmlToPdf;
import com.lingsi.framework.htmltopdf.HtmlToPdfObject;
import com.lingsi.unp.controller.cases.bean.CaseImageVo;
import com.lingsi.unp.dto.NotarialPaperTypeDto;
import com.lingsi.unp.mapper.cases.NotarialPaperFilePathMapper;
import com.lingsi.unp.model.cases.CaseApply;
import com.lingsi.unp.model.cases.NotarialPaperCaseNumber;
import com.lingsi.unp.model.cases.NotarialPaperFilePath;
import com.lingsi.unp.service.notarial.myenum.MyEnum;
import com.lingsi.unp.service.notarial.util.NotarialPaperCaseNumberGenerator;
import com.lingsi.unp.utils.base.DateHelper;
import com.lingsi.unp.utils.base.SpringUtils;
import com.lingsi.unp.utils.io.file.Base64Convert;
import com.lingsi.unp.utils.io.file.FilePath;
import com.lingsi.unp.utils.io.file.FileUtil;
import com.lingsi.unp.utils.qrcode.QrCodeUtil;

import java.io.*;
import java.util.*;

/**
 * 制作公证书
 */
public class NotarialPapersMaker {

    public static final String NotarialSignature="NotarialSignature";
    public static final String NotarialPersonSignature="NotarialPersonSignature";
    public static final String QrCodeImage="QrCodeImage";

    public static final String AppendixOne="AppendixOne";
    public static final String AppendixTwo="AppendixTwo";

    public static final String FileType_NP ="NP";
    public static final String FileType_AP ="AP";//文件类型 NP-公证书名称 AP-附件

    //todo product
//    public static final String viewUrl="http://unp.lingsiscf.com:8080/unp/notarial/view";
//    public static final String server_ip = "http://47.96.236.243:8080";
    //todo sit
    public static final String server_ip = "http://39.98.180.134:8080";
    public static final String viewUrl="http://39.98.180.134:8080/unp/notarial/view";

    /**
     * 获取Html模板
     * @return
     * @throws FileNotFoundException
     */
    public static String getHtmlTemplate(MyEnum template) throws FileNotFoundException {
        String fileName="";
        switch (template){
            case HtmlTemplate :
                fileName=FilePath.getTemplateFileName(template);break;
            default:fileName="";
        }
        FileUtil fileUtil = new FileUtil();
        String htmlTemplate="";
        String str="";
        while((str=fileUtil.readDataStremByFileName(fileName))!=null){
            htmlTemplate+=str;
        }
        return  htmlTemplate;
    }

    /**
     * 填充模板内容
     * @param htmlTemplate
     * @param map
     * @return
     */
    public static String packHtmlTemplate(String htmlTemplate,HashMap<String,String> map){

        if(map!=null){
            for(String key :map.keySet()) {
                try {
                    htmlTemplate = htmlTemplate.replaceAll("\\{\\{"+key+"\\}\\}", map.get(key));
                }catch (Exception e){
                    System.out.println("suntao :"+key+" && "+map.get(key));
                    throw e;
                }
            }
        }
        return htmlTemplate;
    }

    /**
     * 将html转为pdf
     * @param html
     * @param saveFileName
     * @return
     */
    public static boolean htmlToPdfForNotarialPaper(String html,String saveFileName){
        boolean success=false;
        try {
            success = HtmlToPdf.create()
                    .object(HtmlToPdfObject.forHtml(html))
                    .convert(FilePath.getTemplateFilePath(MyEnum.NotarialPdfSavePath)+saveFileName);

        }catch (Exception e){
            e.printStackTrace();
        }catch (Error e){
            e.printStackTrace();
        }
        return success;
    }

    /**
     * 生成二维码
     * @param cpontent 二维码内容
     * @return 二维码路径
     */
    public static String createQrCode(String cpontent) throws Exception {
        String logoPath = FilePath.getTemplateFilePath(MyEnum.QrCodeLogPath);
        String destPath = FilePath.getTemplateFilePath(MyEnum.QrCodeSavePath);
        String fileName=FilePath.getFileName();
        QrCodeUtil.encode(cpontent, logoPath, destPath, fileName,true);
        return  fileName+".jpg";
    }

    public static String getNotarialHtml(CaseApply caseApply) throws FileNotFoundException {
        HashMap<String,String> params = getParasMap(caseApply, false);
        //公证员签章
        params.put(NotarialPersonSignature,"src=\""+ server_ip + "/sign/NotarialPersonSignature01.png"+"\"");
        return getNotarialHtml(params);
    }
    /**
     * 获取公证书Html
     * @param params
     * @return
     * @throws FileNotFoundException
     */
    public static String getNotarialHtml(HashMap params) throws FileNotFoundException {
        //获取公证书这个模板
        String htmlTemplate = getHtmlTemplate(MyEnum.HtmlTemplate);
        //填充公证书内容
        htmlTemplate = packHtmlTemplate(htmlTemplate,params);

        return htmlTemplate;
    }

    /**
     * 生成公证书PDF
     * @param htmlTemplate
     * @param params
     * @return
     * @throws Exception
     */
    public static String createNotarialPaperPdfFromHtml(String htmlTemplate,HashMap params) throws Exception {
        String pdfFileName=FilePath.getFileName()+".pdf";
        if(htmlTemplate==null || "".equals(htmlTemplate)){
            htmlTemplate = getNotarialHtml(params);
        }

        //公证处签章
        params.put(NotarialSignature,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.NotarialSignaturePath))+"\"");

        //公证员签章
        params.put(NotarialPersonSignature,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.NotarialPersonSignature01))+"\"");

        //生成二维码
        String fileName=createQrCode(viewUrl+"/"+FilePath.getRandomCode(40)+"/"+pdfFileName);

        //二维码填充
        params.put(QrCodeImage,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.QrCodeSavePath)+fileName)+"\"");

//        String oneStr = params.get(AppendixOne).toString();
//        String twoStr = params.get(AppendixTwo).toString();
//
//        oneStr = oneStr.substring(oneStr.lastIndexOf("/") +1, oneStr.length()-1);
//        twoStr = twoStr.substring(twoStr.lastIndexOf("/") +1, twoStr.length()-1);
//
//        System.out.println("srccc=" + oneStr);
//        System.out.println("srccc=" + twoStr);
//
//        params.put(AppendixOne,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+oneStr)+"\"");
//        params.put(AppendixTwo,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+twoStr)+"\"");

        htmlTemplate = packHtmlTemplate(htmlTemplate,params);

        htmlToPdfForNotarialPaper(htmlTemplate,pdfFileName);

        return pdfFileName;
    }

    /**
     * 得到公证文书的二进制文件
     * @param caseId
     * @return
     * @throws IOException
     */
    public static String getNotarialPaperPdfByteString(Long caseId) throws IOException {
        return FileUtil.getByteString(getNotarialPaperfilePath(caseId));
    }

    /**
     * 得到公证书路径
     * @param caseId
     * @return
     */
    public static String getNotarialPaperfilePath(Long caseId) throws FileNotFoundException {
        NotarialPaperFilePathMapper notarialPaperFilePathMapper = SpringUtils.getBean(NotarialPaperFilePathMapper.class);
        List<NotarialPaperFilePath> records = notarialPaperFilePathMapper.selectByCaseId(caseId);

        System.out.println(records.size());
        System.out.println(JSON.toJSONString(records));

        if(records!=null && records.size()>0) {
            for (NotarialPaperFilePath record : records) {
                System.out.println(JSON.toJSONString(record));
                if (record.getFileType().equals("NP")) {
                    return FilePath.getTemplateFilePath(MyEnum.NotarialPdfSavePath) + record.getFilePath();
                }
            }
        }
        return null;
    }

    /**
     * 得到附件列表信息
     * @param caseId
     * @return
     */
    public static List<CaseImageVo> getAppendixFileListForShow(Long caseId){
        List<NotarialPaperFilePath> list= getAppendixFileList(caseId);
        List<CaseImageVo> result = new ArrayList<>();
        Map<String, Integer> count = new HashMap<>();

        for(NotarialPaperFilePath record :list){
            CaseImageVo vo = new CaseImageVo();
            if (count.containsKey(record.getFileName())){
                count.put(record.getFileName(), count.get(record.getFileName()) + 1);
            }else {
                count.put(record.getFileName(), 1);
            }
            vo.setFilePath(record.getFilePath());
            vo.setShowName(record.getFileName() + "（第" + count.get(record.getFileName()) + "页）");

            result.add(vo);
        }
        return  result;
    }

    /**
     * 得到附件列表信息
     * @param caseId
     * @return
     */
    public static List<NotarialPaperFilePath> getAppendixFileList(Long caseId){
        NotarialPaperFilePathMapper notarialPaperFilePathMapper = SpringUtils.getBean(NotarialPaperFilePathMapper.class);
        List<NotarialPaperFilePath> records = notarialPaperFilePathMapper.selectByCaseId(caseId);
        if(records==null || records.size()<=0 ) {
            return null;
        }
        Iterator<NotarialPaperFilePath> iterator = records.iterator();
        while(iterator.hasNext()){
            NotarialPaperFilePath record = iterator.next();
            if(!record.getFileType().trim().equalsIgnoreCase("AP")){
                iterator.remove();
            }
        }
        return records;
    }


    /**
     * 得到附件的文件路径
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getAppendixFilePath(String fileName) throws IOException {
        String filePath = FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+fileName;
        return filePath;
    }


    /**
     * 得到附件的二进制文件
     * @param fileName
     * @return
     * @throws IOException
     */
    public static String getAppendixByteString(String fileName) throws IOException {
        String filePath = FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+fileName;
        return FileUtil.getByteString(filePath);
    }


    public static HashMap<String,String>  getParasMap(CaseApply caseApply, boolean pdfFlag){
        HashMap<String,String> paras = new HashMap<>();

        paras.put("year", DateHelper.getCureentYear());
        paras.put("number", "xxxx");

        paras.put("applicant", "灵君科技（衢州）有限公司");
        paras.put("businessLicense", "91330800MA2DGQW636");
        paras.put("businessAddress", "浙江省衢州市白云中大道88幢301室");
        paras.put("legalPerson", "赵中华");
        paras.put("gender", "男");
        paras.put("legalPersonIdCard", "33032319721110001x");
        paras.put("legalPersonAddress", "浙江省温州市鹿城区五马街道府前街鸿华大厦A座801室");

        paras.put("applicant", "灵君科技（衢州）有限公司");

        paras.put("bank", caseApply.getPersonIdInvolved());
        paras.put("dispute", caseApply.getCaseDispute());
        paras.put("address", caseApply.getInvolvedAddress());

        //过时代码，直接set固定值，不可取
//        paras.put("bank", "中国工商银行股份有限公司衢州分行");
//        paras.put("bank", "中国银行股份有限公司台州市分行");
//        paras.put("dispute", "信用卡纠纷");
//        paras.put("address", "浙江省衢州市上街66号附属楼2楼");
//        paras.put("address", "浙江省衢州市柯城区花园东大道165号西联大厦10层");

        try {
            paras.put("year1", DateHelper.getYearChi(caseApply.getForensicStartTime()));
            paras.put("month1", DateHelper.getMonthChi(caseApply.getForensicStartTime()));
            paras.put("day1", DateHelper.getDayChi(caseApply.getForensicStartTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        paras.put("assistantManager", caseApply.getAssistNotaryName()); //公证助理
        paras.put("bankStaff", caseApply.getAssistInvolvedName());  //银行工作人员
        paras.put("idCard1", caseApply.getAssistInvolvedIdCardNo());    //银行身份证号

        paras.put("creditCardHolder", caseApply.getRespondent());
        paras.put("idCard2", caseApply.getRespondentId());
        paras.put("notary", "");//此处需要变为空
        paras.put("year2", DateHelper.getCurrentYearChi());
        paras.put("month2", DateHelper.getCurrentMonthChi());
        paras.put("day2", DateHelper.getCurrentDayChi());

        List<NotarialPaperFilePath> filePathList = NotarialPapersMaker.getExistsAp(caseApply.getId());
        paras.put("attachmentList", NotarialPapersMaker.getExistsApName(filePathList));
        paras.put("attachmentDetail", NotarialPapersMaker.getAttachmentDetail(filePathList, pdfFlag));


        //todo delete
//        paras.put("attachment1", "中国银行信用卡客户申请进度查询表");
//        paras.put("attachment2", "信用卡领用合约补充条款");
//        paras.put("attachment3", "中国银行信用卡客户申请进度查询表");
//        paras.put("attachment4", "信用卡领用合约补充条款");


//        ##params.put(NotarialSignature,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.NotarialSignaturePath))+"\"");
//        List<NotarialPaperFilePath> AppendixList =  getAppendixFileList(caseApply.getId());
//        for(NotarialPaperFilePath filePath : AppendixList){
//            System.out.println("srcc=" + filePath.getFilePath());
//
//            if(filePath.getFileName().equals("中国银行信用卡客户申请进度查询表")){
//                paras.put(AppendixOne, "src=\"" + "http://47.96.236.243:8080/pictures/" + filePath.getFilePath() + "\"");
//                ##paras.put(AppendixOne, "src=\"" + "http://39.98.180.134:8080/pictures/" + filePath.getFilePath() + "\"");
//                ##paras.put(AppendixOne,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+filePath.getFilePath())+"\"");

//                LogManager.getLogger().info(paras.get(AppendixOne));
//            }else if(filePath.getFileName().equals("信用卡领用合约补充条款")){
//                paras.put(AppendixTwo, "src=\"" + "http://47.96.236.243:8080/pictures/" + filePath.getFilePath() + "\"");
//                ##paras.put(AppendixTwo, "src=\"" + "http://39.98.180.134:8080/pictures/" + filePath.getFilePath() + "\"");
//                ##paras.put(AppendixTwo,"src=\""+Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+filePath.getFilePath())+"\"");
//                LogManager.getLogger().info(paras.get(AppendixTwo));

//            }
//        }



        return paras;
    }

    /**
     * 公证书信息入库 ps: 主任审批结束时调用该方法，生成公证书
     * @param caseApply
     * @throws Exception
     */
    public static String  notarialPaperHandle(CaseApply caseApply, NotarialPaperTypeDto notarialPaperTypeDto) throws Exception {
        HashMap<String,String> paras =getParasMap(caseApply, true);
        //获取案号
        NotarialPaperCaseNumber notarialPaperCaseNumber = NotarialPaperCaseNumberGenerator.getNotarialPaperCaseNumber(caseApply.getId());
        paras.put("year",notarialPaperCaseNumber.getYear());
        paras.put("number",String.valueOf(notarialPaperCaseNumber.getId())  );

        //生成公证书
        String filePath= createNotarialPaperPdfFromHtml(null, paras);


        NotarialPaperFilePath record = new NotarialPaperFilePath();
        record.setCaseId( caseApply.getId());
        record.setFileName(notarialPaperTypeDto.getPaperName());//前端展示用的名字，和文件具体位置无关
        record.setFilePath(filePath);//filePath是系统里存储的公证书名称，为随机流水号
        record.setFileType(notarialPaperTypeDto.getPaperType());
        record.setItemNo(1);
        NotarialPaperFilePathMapper notarialPaperFilePathMapper = SpringUtils.getBean(NotarialPaperFilePathMapper.class);
        notarialPaperFilePathMapper.insert(record);
        return filePath;
    }

    public static void save(String byteString,String fileName) throws IOException {

        byte[] fileByte1 = byteString.getBytes("ISO-8859-1");

        File outfile = new File(fileName);
        if (!outfile.exists()) {
            outfile.createNewFile();
        }
        DataOutputStream fw = new DataOutputStream(new FileOutputStream(
                outfile));
        fw.write(fileByte1);
        fw.close();
    }

    public static List<NotarialPaperFilePath> getExistsAp(Long caseId){
        Map param = new HashMap(2);
        param.put("caseId", caseId);
        param.put("fileType", "AP");
        NotarialPaperFilePathMapper notarialPaperFilePathMapper = SpringUtils.getBean(NotarialPaperFilePathMapper.class);
        return notarialPaperFilePathMapper.selectApOrderByItemNo(param);
    }

    public static String getExistsApName(List<NotarialPaperFilePath> list)
    {
        String attachmentList = "";
        List<String> nameList = new ArrayList<>();
        int k = 1;
        for (NotarialPaperFilePath filePath : list)
        {
            if (!nameList.contains(filePath.getFileName())) {
                nameList.add(filePath.getFileName());
                if (k ==1 ){
                    attachmentList = "<p style=\"text-indent: 43px;margin: 0;padding: 0;\">"
                            +"<span style=\"font-size: 21px;font-family: 仿宋\">附件：1、"+ filePath.getFileName() +"</span>"
                            + "</p>";
                }else {
                    attachmentList += "<p style=\"text-indent: 107px;margin: 0;padding: 0;\">"
                            +"<span style=\"font-size: 21px;font-family: 仿宋\">" + k + "、" + filePath.getFileName() +"</span>"
                            + "</p>";
                }
                k ++;
            }
        }
        System.out.println("attachmentList=" + attachmentList);
        return attachmentList;
    }

    public static String getAttachmentDetail(List<NotarialPaperFilePath> list, boolean pdfFlag){

        StringBuffer result = new StringBuffer();
        List<String> nameList = new ArrayList<>();
        Map<String , Integer> paperCount = new HashMap();
        Map detail = new HashMap();
        for (NotarialPaperFilePath filePath : list)
        {
            if (!nameList.contains(filePath.getFileName())) {
                nameList.add(filePath.getFileName());
                paperCount.put(filePath.getFileName(), 1);
            }else {
                paperCount.put(filePath.getFileName(), paperCount.get(filePath.getFileName()) + 1);
            }
        }

        for (String name : nameList)
        {
            int k = 1;
            for(NotarialPaperFilePath filePath : list)
            {
                if (name.equals(filePath.getFileName()))
                {
                    StringBuffer sb = new StringBuffer();

                    sb.append("<p style=\"text-indent: 43px;margin: 0;padding: 0;\">")
                            .append("<span style=\"font-size: 18px;font-family: 仿宋\">第" + k + "页（共" + paperCount.get(name)  + "页）</span>")
                            .append("</p>");
                    if (pdfFlag){
                        sb.append("<p style=\"text-align:center;text-autospace:none;margin: 0;padding: 0;\">")
                                .append("<img src=\"" + Base64Convert.GetImageStr(FilePath.getTemplateFilePath(MyEnum.AppendixFileSavePath)+filePath.getFilePath())+ "\" style=\"width: 90%\"/>")
                                .append("</p>");
                    }else {

                        sb.append("<p style=\"text-align:center;text-autospace:none;margin: 0;padding: 0;\">")
                            .append("<img src=\"" + server_ip + "/pictures/" + filePath.getFilePath() + "\" style=\"width: 90%\"/>")
                                .append("</p>");
                    }
                    if (k == 1) {
                        detail.put(name, sb.toString());
                    }else {
                        detail.put(name, detail.get(name) + sb.toString());
                    }
                    k ++;
                }
            }

        }

        Iterator<String> it = detail.keySet().iterator();
        int attachmentNo = 1;
        while (it.hasNext())
        {
            String key = it.next();
            String value = detail.get(key).toString();
            result.append("<p style=\"text-indent: 43px;margin: 0;padding: 0;\">")
                    .append("<span style=\"font-size: 21px;font-family: 仿宋\">附件：<span>" +attachmentNo+ "</span>、<span>"+ key +"</span></span>")
                    .append("</p>")
                    .append(value);

            attachmentNo ++;
        }

        return result.toString();
    }

    public static void main(String[] args) throws Exception {
//        String filePath = FilePath.getTemplateFilePath(MyEnum.NotarialPdfSavePath)+"155610541920657081742.pdf";
//        File file = new File(filePath);
//        FileInputStream fis = new FileInputStream(file);
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        byte[] b = new byte[1024];
//        int len = -1;
//        while((len = fis.read(b)) != -1) {
//            bos.write(b, 0, len);
//        }
//        byte[] fileByte = bos.toByteArray();
//
//        String codeType="ISO-8859-1";
//        codeType="UTF-8";
//        String isoString = new String(fileByte,codeType );
//
//        byte[] fileByte1 = isoString.getBytes(codeType);
//
//        File outfile = new File("D:\\a-b1221.pdf");
//        if (!outfile.exists()) {
//            outfile.createNewFile();
//        }
//        DataOutputStream fw = new DataOutputStream(new FileOutputStream(
//                outfile));
//        fw.write(fileByte1);
//        fw.close();

        String s = "/home/folder/notarialpdf/155652601837597038747.pdf";
        System.out.println("/home/folder/notarialpdf/155652601837597038747.pdf".substring(s.lastIndexOf("/")+1 ));

    }
}
