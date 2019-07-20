package com.lingsi.unp.controller.bill;


import com.lingsi.unp.exception.DMException;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.service.bill.BillInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Zzy
 * @description
 */
@Api(value="账单管理",tags = {"账单基础服务-账单增删改查"},position= 1 )
@RestController
@RequestMapping( value ="/test/learn/bill")
public class BillInfo_Controller {
    @Autowired
    BillInfoService billInfoService;

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "新增账单", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "billNo", value = "账单编号", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "billAmt", value = "账单数量", required = true, dataType = "String"),

    })

    public CommonRes addBill(String billNo, String buyerLicenseNo, String buyerName, String sellerName, Double billAmt, Double billBalance, String billDate, String payDate, String orderStartDate, String orderEndDate, String billStatus, String status, String contractStatusUpdate, String scfBillStatusUpdate, String inputTime, String updateTime) {

        try {
            if (billInfoService.addBill(billNo, buyerLicenseNo, buyerName, sellerName, billAmt, billBalance, billDate, payDate, orderStartDate, orderEndDate, billStatus, status, contractStatusUpdate, scfBillStatusUpdate, inputTime, updateTime) == 1) {
                return CommonRes.http_ok("新增账单成功").success();
            } else {
                return CommonRes.http_ok("新增账单失败").fail();
            }
        } catch (DMException e) {
            return CommonRes.http_ok("新增账单失败|" + e.getMessage()).fail();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonRes.http_error("新增账单异常");
        }
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据账单号查询", notes = "billNo为账单号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "billNo", value = "账单编号", required = true, dataType = "String")
    })

    public CommonRes getBill(String billNo) {
        try {
            return CommonRes.http_ok("查询账单成功", billInfoService.selectByPrimaryKey(billNo)).success();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据账单号删除", notes = "billNo为账单号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "billNo", value = "账单编号", required = true, dataType = "String")
    })

    public CommonRes deleteBill(String billNo) {
        try {
            if (billInfoService.deleteByPrimaryKey(String.valueOf(billNo)) == 1) {
                return CommonRes.http_ok("删除账单成功").success();
            } else {
                return CommonRes.http_ok("删除账单失败").fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonRes.http_error("删除账单失败");
        }
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ApiOperation(value = "根据账单号修改", notes = "billNo为账单号")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "billNo", value = "账单编号", required = true, dataType = "String")
    })

    public CommonRes updateBill(String billNo,String buyerLicenseNo, String buyerName, String sellerName, Double billAmt, Double billBalance, String billDate, String payDate, String orderStartDate, String orderEndDate, String billStatus, String status, String contractStatusUpdate, String scfBillStatusUpdate, String inputTime, String updateTime) {

        try {
            return CommonRes.http_ok("修改用户资料成功", billInfoService.updateByPrimaryKey(billNo,buyerLicenseNo, buyerName, sellerName, billAmt, billBalance, billDate, payDate, orderStartDate, orderEndDate, billStatus, status, contractStatusUpdate, scfBillStatusUpdate, inputTime, updateTime)).success();
        } catch (DMException e) {
            return CommonRes.http_ok("修改失败|" + e.getMessage()).fail();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonRes.http_error(e.getMessage());
        }
    }



}
