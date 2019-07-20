package com.lingsi.unp.service.bill;

import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.mapper.bill.BillInfoMapper;
import com.lingsi.unp.model.bill.BillInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Zzy
 * @description
 */

@Service
@Transactional(transactionManager = "hostTransactionManager")
public class BillInfoService {

    @Autowired
     BillInfoMapper billInfoMapper;

    //新增加账单
    public int addBill(String billNo,String buyerLicenseNo,String buyerName,String sellerName,Double billAmt,Double billBalance,String billDate,String payDate,String orderStartDate,String orderEndDate,String billStatus,String status,String contractStatusUpdate,String scfBillStatusUpdate,String inputTime,String updateTime) {


        try {

                BillInfo p=new BillInfo();
                p.setBillNo(billNo);
                p.setBuyerLicenseNo(buyerLicenseNo);
                p.setBuyerName(buyerName);
                p.setSellerName(sellerName);
                p.setBillAmt(billAmt);
                p.setBillBalance(billBalance);
                p.setBillDate(billDate);
                p.setPayDate(payDate);
                p.setOrderStartDate(orderStartDate);
                p.setOrderEndDate(orderEndDate);
                p.setBillStatus(billStatus);
                p.setStatus(status);
                p.setScfBillStatusUpdate(scfBillStatusUpdate);
                p.setContractStatusUpdate(contractStatusUpdate);
                p.setUpdateTime(updateTime);
                p.setInputTime(inputTime);

                if (billInfoMapper.insert(p) == 1) {
                    return 1;
                } else
                    throw new DMException("新增用户失败");
            }catch (RuntimeException e){
                e.printStackTrace();
                throw new DMException(e);
            }catch (Exception e){
                e.printStackTrace();
                throw e;
            }
        }


    //根据主键查询所有
    public BillInfo selectByPrimaryKey(String billNo){
        BillInfo billInfo = billInfoMapper.selectByPrimaryKey(billNo);

        if(billInfo==null) {

            return null;
        }else{
            return billInfo;
        }


    }


    //根据主键账单号删除
    public int deleteByPrimaryKey(String billNo){
        try {
            if(billInfoMapper.deleteByPrimaryKey(billNo)==1)
                return 1;
            else
                throw new DMException("删除账单失败");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new DMException(e);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    //根据主键账单修改
    public int updateByPrimaryKey(String billNo,String buyerLicenseNo,String buyerName,String sellerName,Double billAmt,Double billBalance,String billDate,String payDate,String orderStartDate,String orderEndDate,String billStatus,String status,String contractStatusUpdate,String scfBillStatusUpdate,String inputTime,String updateTime)
    {


        try {

            BillInfo p = billInfoMapper.selectByPrimaryKey(billNo);


            p.setBuyerLicenseNo(buyerLicenseNo);
            p.setBuyerName(buyerName);
            p.setSellerName(sellerName);
            p.setBillAmt(billAmt);
            p.setBillBalance(billBalance);
            p.setBillDate(billDate);
            p.setPayDate(payDate);
            p.setOrderStartDate(orderStartDate);
            p.setOrderEndDate(orderEndDate);
            p.setBillStatus(billStatus);
            p.setStatus(status);
            p.setScfBillStatusUpdate(scfBillStatusUpdate);
            p.setContractStatusUpdate(contractStatusUpdate);
            p.setUpdateTime(updateTime);
            p.setInputTime(inputTime);

            if (billInfoMapper.updateByPrimaryKey(p) == 1) {
                return 1;
            } else
                throw new DMException("修改账单失败");
        }catch (RuntimeException e){
            e.printStackTrace();
            throw new DMException(e);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }




}
