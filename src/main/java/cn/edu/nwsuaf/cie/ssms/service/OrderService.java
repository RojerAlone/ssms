package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

import java.util.Date;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public interface OrderService {

    Result getById(int id);

    Result getMyOrders(int page, int nums);

    Result getNotPaiedOrders(int page, int nums);

    Result getPaiedOrders(int page, int nums);

    Result getCanceledOrders(int page, int nums);

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

    Result getCost();

    Result getSportTime();

}
