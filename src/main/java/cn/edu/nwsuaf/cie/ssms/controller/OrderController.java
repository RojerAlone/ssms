package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@RestController
@RequestMapping(value = "order")
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserHolder userHolder;

    @PutMapping("/order")
    public Result order(@RequestParam int gid, @RequestParam long startTime, @RequestParam long endTime) {
        return orderService.order(userHolder.getUser().getUid(), gid, startTime, endTime);
    }

    @PostMapping("cancel")
    public Result cancel(@RequestParam int orderId) {
        return orderService.cancel(userHolder.getUser().getUid(), orderId);
    }

}
