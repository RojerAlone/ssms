package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RojerAlone
 * @Date 2017-12-12 23:18
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @PutMapping("/addSpecial")
    public Result addSpecialUser(@RequestParam("uid") String uid) {
        return null;
    }

}
