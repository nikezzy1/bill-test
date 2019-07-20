package com.lingsi.unp.utils.http;

import com.lingsi.unp.exception.HttpProcessException;

/**
 * 统一管理连接池和参数配置
 *
 * @author wuzu
 * @date 2019/03/29
 */
public class HcbManager {
    private static HCB hcb = null;

    public static HCB getHcb() throws HttpProcessException {
        if (hcb == null) {
            initHcb();
        }
        return hcb;
    }

    private synchronized static void initHcb() throws HttpProcessException {
        if (hcb == null) {
            // 配置生成HttpClient时所需参数（超时、连接池、重试）
            hcb = HCB.custom().timeout(HttpConsts.timeout).pool(HttpConsts.maxTotal, HttpConsts.maxPerRoute)
                    .retry(HttpConsts.tryTimes, HttpConsts.RETRYWHENINTERRUPTEDIO);
        } else {
            return;
        }

    }
}
