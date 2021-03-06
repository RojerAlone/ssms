package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@RestController
@RequestMapping(value = "/order")
public class OrderController extends AbstractController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public Result order(@RequestParam int gid, @RequestParam String startTime, @RequestParam String endTime) {
        return orderService.order(gid, startTime, endTime);
    }

    @PutMapping("/cancel")
    public Result cancel(@RequestParam int orderId) {
        return orderService.cancel(orderId);
    }

    @GetMapping("/{id}")
    public Result getBydId(@PathVariable("id") int id) {
        return orderService.getById(id);
    }

    @GetMapping("/paying")
    public Result getPayingOrders(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.getNotPaiedOrders(page, nums);
    }

    @GetMapping("/orders")
    public Result getOrderedOrders(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.getPaiedOrders(page, nums);
    }

    @GetMapping("/canceled")
    public Result getCanceledOrders(@RequestParam(value = "page", defaultValue = "1") int page,
                                    @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.getCanceledOrders(page, nums);
    }

}
