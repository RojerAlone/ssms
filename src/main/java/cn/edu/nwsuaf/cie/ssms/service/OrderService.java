package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public interface OrderService {

    Result getById(int id);

    Result getNotPaiedOrders(int page, int nums);

    Result getAllNotPaidOrders(int page, int nums);

    Result getAllPaidOrders(int page, int nums);

    Result searchByUid(String uid, int page, int nums);

    Result getPaiedOrders(int page, int nums);

    Result getCanceledOrders(int page, int nums);

    /**
     * 预订场地
     */
    Result order(int gid, String startTime, String endTime);

    Result orderAndPay(int gid, String startTime, String endTime);

    /**
     * 预订健美操室
     *
     * @param date 预订的日期，格式为 yyyy-MM-dd
     * @param time 课余时间还是晚自习，0 表示课余时间，1 表示晚自习
     */
    Result orderGymnastics(String date, int time);

    /**
     * 取消预订
     */
    Result cancel(int orderId);

    /**
     * 支付订单
     */
    Result pay(int orderId);

    Result getPersonalSportTime();

    Result getAllSportTime();

}
