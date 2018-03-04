package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.AdminService;
import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author RojerAlone
 * @Date 2017-12-12 23:18
 */
@RestController
public class AdminController extends AbstractController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login")
    public Result login(@RequestParam("uid") String uid, @RequestParam("password") String password, HttpServletResponse response) {
        Result result = adminService.login(uid, password);
        if (result.isSuccess()) {
            Cookie cookie = new Cookie("token", (String) result.getResult());
            cookie.setPath("/");
            cookie.setMaxAge((int) (TimeUtil.ONE_DAY * 7 / 1000));
            response.addCookie(cookie);
            return Result.success();
        }
        return result;
    }

    @PutMapping("/admin/{uid}")
    public Result addAdmin(@PathVariable("uid") String uid) {
        return adminService.addAdmin(uid);
    }

    @DeleteMapping("/admin/{uid}")
    public Result removeAdmin(@PathVariable("uid") String uid) {
        return adminService.addAdmin(uid);
    }

    @PutMapping("/worker/{uid}")
    public Result addWorker(@PathVariable("uid") String uid) {
        return adminService.addWorker(uid);
    }

    @DeleteMapping("/worker/{uid}")
    public Result removeWorker(@PathVariable("uid") String uid) {
        return adminService.removeWorker(uid);
    }

}
