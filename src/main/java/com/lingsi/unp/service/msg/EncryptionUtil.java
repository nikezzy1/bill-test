package com.lingsi.unp.service.msg;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.function.Function;
import java.util.function.Supplier;

public class EncryptionUtil {
    private static String getSHA1EncryptedString(String inputStr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA");
        messageDigest.update(inputStr.getBytes());
        Supplier<String> convertMDToSHA1 = () -> new BigInteger(messageDigest.digest()).toString().toUpperCase();
        return convertMDToSHA1.get();

    }


    // 短信接口的sign计算
    public static <T> String calculateSign(String appId, String appSecret, long ts, T t) throws Exception {

       /// checkAppId(appId, ts);

        Function<String, String> getParams = (x) -> new StringBuffer(x).append(appSecret).append(t.toString()).toString();
        try {
            return getSHA1EncryptedString(getParams.apply(appId));
        } catch (NoSuchAlgorithmException e) {
            throw new  Exception("算法错误");
        }
    }


    public static void main(String[] args) throws  Exception {

        long sy = System.currentTimeMillis();
        System.out.println("sy="+sy);
        // sendCaptcha
      /*  SendCaptchaReq sendCaptchaReq = new SendCaptchaReq();
        sendCaptchaReq.setAppId("arb");
        sendCaptchaReq.setCode("1234");
        sendCaptchaReq.setPhone("18801541712");
        sendCaptchaReq.setTs(sy);
        System.out.println(calculateSign("arb", "1bDqmBFRt112rz", 1551929717991l, sendCaptchaReq));
*/
        // checkCaptcha
        CheckCaptchaReq checkCaptchaReq = new CheckCaptchaReq();
        checkCaptchaReq.setPhone("18801541712");
        checkCaptchaReq.setAppId("arb");
        checkCaptchaReq.setTs(sy);
        checkCaptchaReq.setCode("12345");
        System.out.println(calculateSign("arb", "1bDqmBFRt112rz", 1551929717991l, checkCaptchaReq));

        /*// uploadFile
        System.out.println(calculateSign("arb", "1bDqmBFRt112rz", 1551929717991l, new UploadFileReq("test.pdf", 1551929717991l)));

        // downloadFile
        System.out.println(calculateSign("arb", "1bDqmBFRt112rz", 1551929717991l, new DownloadFileReq("test.pdf", 1551929717991l)));*/
    }
}
