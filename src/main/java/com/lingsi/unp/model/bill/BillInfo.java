package com.lingsi.unp.model.bill;

import lombok.Data;

/**
 * @author Zzy
 * @description
 */

@Data
public class BillInfo {
    private String billNo;

    private String buyerLicenseNo;

    private String buyerName;

    private String sellerName;

    private Double billAmt;

    private Double billBalance;

    private String billDate;

    private String payDate;

    private String orderStartDate;

    private String orderEndDate;

    private String billStatus;

    private String status;

    private String contractStatusUpdate;

    private String scfBillStatusUpdate;

    private String inputTime;

    private String updateTime;


}

