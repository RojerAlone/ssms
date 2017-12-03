package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

import java.util.Date;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public interface OrderService {

    /**
     * 预订场地
     * @param uid
     * @param gid
     */
    Result order(String uid, int gid, Date startTime, Date endTime);

    /**
     * 取消预订
     * @param uid
     * @param orderId
     * @return
     */
    Result cancel(String uid, int orderId);

    /**
     * 支付订单
     * @param orderId
     * @return
     */
    Result pay(int orderId);

}
