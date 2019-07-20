package com.lingsi.unp.service.notarial.myenum;

/**
 * 文书模板
 */
public enum  MyEnum {

    HtmlTemplate("/notarialTemplate/htmlTemplate.txt"),

    QrCodeLogPath("/logo/qrCodeLog.png"),
    QrCodeSavePath("/qrCode/"),

    NotarialPdfSavePath("/notarialpdf/"),
    NotarialZipSavePath("/zip/"),

    NotarialSignaturePath("/signature/NotarialSignature.png"),
    NotarialPersonSignature01("/signature/NotarialPersonSignature01.png"),
    NotarialPersonSignature02("/signature/NotarialPersonSignature02.png"),
    NotarialPersonSignature03("/signature/NotarialPersonSignature03.png"),

    AppendixFileSavePath("/casePhoto/");

    private String value;

    MyEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
