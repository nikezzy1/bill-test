package com.lingsi.unp.service.msg;

import com.alibaba.fastjson.JSON;
import com.lingsi.unp.utils.http.HttpClientUtil;
import lombok.Data;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

public class MessageService {

    public static final String msgCodeSendUrl="http://t_service.lingsiscf.com/sendCaptcha";
    public static final String msgCodeCheckUrl="http://t_service.lingsiscf.com/checkCaptcha";
    public static final String appSecret="1bDqmBFRt112rz";
    public static final String appId="arb";

    public static boolean sendMessageCode(String telePhone,String code) throws Exception {
        MultiValueMap<String, Object> bodyParams = new LinkedMultiValueMap<String, Object>();
        long ts =System.currentTimeMillis();

        bodyParams.add("appId",appId);
        bodyParams.add("phone",telePhone);
        bodyParams.add("code",code);
        bodyParams.add("ts",ts);


        SendCaptchaReq sendCaptchaReq = new SendCaptchaReq();
        sendCaptchaReq.setAppId(appId);
        sendCaptchaReq.setCode(code);
        sendCaptchaReq.setPhone(telePhone);
        sendCaptchaReq.setTs(ts);
        String sign = EncryptionUtil.calculateSign(appId, appSecret, ts, sendCaptchaReq);

        Map header = new HashMap();
        header.put("sign", sign);

        // 发送GET请求
        String response = HttpClientUtil.doPostAcceptContentType(msgCodeSendUrl, sendCaptchaReq, header);
        System.out.println("AuthenticContractSignRessp=" + response);

        AppCommonResp re =  JSON.parseObject(response, AppCommonResp.class);
        if("200".equals(re.getCode())){
            return true;
        }else
            throw new Exception(re.toString());
    }


    public static boolean checkMessageCode(String telePhone,String code) throws Exception {
        MultiValueMap<String, Object> bodyParams = new LinkedMultiValueMap<String, Object>();
        long ts =System.currentTimeMillis();

        bodyParams.add("appId",appId);
        bodyParams.add("phone",telePhone);
        bodyParams.add("code",code);
        bodyParams.add("ts",ts);

        CheckCaptchaReq checkCaptchaReq = new CheckCaptchaReq();
        checkCaptchaReq.setAppId(appId);
        checkCaptchaReq.setCode(code);
        checkCaptchaReq.setPhone(telePhone);
        checkCaptchaReq.setTs(ts);
        String sign = EncryptionUtil.calculateSign(appId, appSecret, ts, checkCaptchaReq);

        Map header = new HashMap();
        header.put("sign", sign);

        // 发送GET请求
        String response = HttpClientUtil.doPostAcceptContentType(msgCodeCheckUrl, checkCaptchaReq, header);
        System.out.println("AuthenticContractSignRessp=" + response);

        AppCommonResp re =  JSON.parseObject(response, AppCommonResp.class);
        if("200".equals(re.getCode())){
            return true;
        }else if("6000002".equals(re.getCode())){
            return false;
        }else
            throw new Exception(re.toString());
    }
    public static void main(String[] args) throws Exception {

        System.out.println("req1="+sendMessageCode("18801541712","8888"));
        System.out.println("req2="+checkMessageCode("18801541712","8888"));
        System.out.println("req3="+checkMessageCode("18801541712","88889"));

    }
    @Data
    public class AppCommonResp {
        private String code;
        private String msg;
        private Object data;
        private String nextPage;
    }
}
