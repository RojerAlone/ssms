package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.RootService;
import cn.edu.nwsuaf.cie.ssms.util.LoginResultUtil;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangrenjie on 2018-03-04
 */
@RestController
@RequestMapping("/root")
public class RootController {

    @Autowired
    private RootService rootService;

    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password,
                        HttpServletResponse response) {
        return LoginResultUtil.parseResult(rootService.login(username, password), response, 1);
    }

    @PutMapping("/admin/{uid}")
    public Result addAdmin(@PathVariable("uid") String uid) {
        return rootService.addAdmin(uid);
    }

    @DeleteMapping("/admin/{uid}")
    public Result removeAdmin(@PathVariable("uid") String uid) {
        return rootService.removeAdmin(uid);
    }

}
