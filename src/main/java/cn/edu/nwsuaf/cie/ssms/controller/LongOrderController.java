package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.service.LongOrderService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangrenjie on 2017-12-12
 * 暂时不考虑长期订单
 */
@Deprecated
//@RestController
@RequestMapping("/longorder")
public class LongOrderController {

    @Autowired
    private LongOrderService longOrderService;

    @GetMapping("/all")
    public Result getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                         @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return longOrderService.getAll(page, nums);
    }

    @PutMapping("/add")
    public Result add(@RequestParam("gid") int gid,
                      @RequestParam("startDate") long startDate,
                      @RequestParam(value = "endDate", required = false) long endDate,
                      @RequestParam(value = "startTime", required = false) Long startTime,
                      @RequestParam(value = "endTime", required = false) Long endTime,
                      @RequestParam("weekday") int weekday) {
        LongOrder longOrder = new LongOrder(gid, startDate, endDate, startTime, endTime, weekday);
        return longOrderService.order(longOrder);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable("id") int id) {
        return longOrderService.delete(id);
    }

}
