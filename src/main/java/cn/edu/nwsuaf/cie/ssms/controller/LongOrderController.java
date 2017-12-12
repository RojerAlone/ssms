package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.service.LongOrderService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangrenjie on 2017-12-12
 */
@RestController
@RequestMapping("/longorder")
public class LongOrderController {

    @Autowired
    private LongOrderService longOrderService;

    @GetMapping("/all")
    public Result getAll(@RequestParam(value = "nums", defaultValue = "10") int nums) {
        return longOrderService.getAll(nums);
    }

    @PutMapping("/add")
    public Result add() {
        LongOrder longOrder = new LongOrder();
        return longOrderService.insert(longOrder);
    }

}
