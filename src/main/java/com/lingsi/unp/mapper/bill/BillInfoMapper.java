package com.lingsi.unp.mapper.bill;

import com.lingsi.unp.model.bill.BillInfo;

/**
 * @author Zzy
 * @description
 */
public interface BillInfoMapper {
    int deleteByPrimaryKey(String billNo);

    int insert(BillInfo p);

    BillInfo selectByPrimaryKey(String billNo);

    int updateByPrimaryKey(BillInfo p);


}
