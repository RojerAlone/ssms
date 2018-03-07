package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.RootService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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
        Result result = rootService.login(username, password);
        if (result.isSuccess()) {
            Cookie cookie = new Cookie("token", (String) result.getResult());
            cookie.setPath("/");
            cookie.setMaxAge((int) (TimeUtil.ONE_DAY / 1000)); // root 权限比较重要，token 有效期为 1 天
            response.addCookie(cookie);
            return Result.success();
        }
        return result;
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
