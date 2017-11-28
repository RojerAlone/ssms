package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.model.Result;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@RestController
@RequestMapping(value = "order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PutMapping("/order")
    public Result order() {
        return Result.success("order");
    }

    @PostMapping("cancel")
    public Result cancel() {
        return Result.success("cancel");
    }

}
