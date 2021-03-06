package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.service.MessageService;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author RojerAlone
 * @Date 2017-12-12 23:18
 */
@RestController
@RequestMapping("/admin")
public class AdminController extends AbstractController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserHolder userHolder;

    @PostMapping("/login")
    public Result login(@RequestParam String uid, @RequestParam String authToken, HttpServletResponse response) {
        if (userHolder.getUser() != null && userHolder.getUser().getUid().equals(uid)) {
            return Result.success();
        }
        if (!UserAccessUtil.isAdmin(uid)) {
            return Result.error(MsgCenter.ERROR_AUTH);
        }
        return LoginResultUtil.parseResult(uid, CommonService.login(uid, authToken), response, 1);
    }

    @PutMapping("/payment/{id}")
    public Result payOrder(@PathVariable("id") int orderId) {
        return orderService.pay(orderId);
    }

    @PostMapping("/gymnastics")
    public Result orderGymnastics(@RequestParam String date, @RequestParam int time) {
        return orderService.orderGymnastics(date, time);
    }

    @GetMapping("/gymnastics")
    public Result getGymnastics(@RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.getGymnasticsOrders(page, nums);
    }

    @DeleteMapping("/gymnastics/{id}")
    public Result deleteGymnasticsOrder(@PathVariable("id") int orderId) {
        return orderService.cancel(orderId);
    }

    @GetMapping("/sport")
    public Result getSport() {
        return orderService.getAllSportTime();
    }

    @PostMapping("/message")
    public Result addMessage(@RequestParam(value = "title", required = false) String title, @RequestParam("content") String content) {
        return messageService.addMessage(title, content);
    }

    @DeleteMapping("/message/{id}")
    public Result deleteMessage(@PathVariable("id") int id) {
        return messageService.deleteMessage(id);
    }

    @GetMapping("/order/notpaid")
    public Result getAllNotPaidOrders(@RequestParam(value = "page", defaultValue = "1") int page,
                                  @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.getAllNotPaidOrders(page, nums);
    }

    @GetMapping("/order/paid")
    public Result getAllPaidOrders(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.getAllPaidOrders(page, nums);
    }

    @GetMapping("/order/search")
    public Result searchOrdersByUid(@RequestParam String uid,
                                    @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return orderService.searchByUid(uid, page, nums);
    }
}
