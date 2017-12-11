package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public interface OrderService {

    Result getById(int id);

    Result getMyOrders(int nums);

    Result getNotPaiedOrders(int nums);

    Result getPaiedOrders(int nums);

    Result getCanceledOrders(int nums);

    /**
     * 预订场地
     */
    Result order(int gid, long startTime, long endTime);

    /**
     * 取消预订
     */
    Result cancel(int orderId);

    /**
     * 支付订单
     */
    Result pay(int orderId);

}
