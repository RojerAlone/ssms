package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.model.Result;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Override
    public Result order(String uid, int gid) {
        return null;
    }

    @Override
    public Result cancel(String uid, int orderId) {
        return null;
    }

    @Override
    public Result pay(int orderId) {
        return null;
    }
}
