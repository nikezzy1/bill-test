package com.lingsi.unp.dto.vo;

import lombok.Data;

/**
 * @description: 广告位招租
 * @author: Wuzu
 * @create: 2019-05-28 14:37
 **/
@Data
public class NotarialPaperTypeVo {
    private Long id;
    private String personInvolved;
    private String businessType;
    private String paperType;
    private String paperName;
//    private String remark;
    private int pageCount;
}
