package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.MessageService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhangrenjie on 2018-04-23
 */
@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping("{id}")
    public Result getMessage(@PathVariable("id") int id) {
        return messageService.getMessageById(id);
    }

    @GetMapping("messages")
    public Result getMessageByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "nums", defaultValue = "10") int nums) {
        return messageService.getMessageByPage(page, nums);
    }

}
