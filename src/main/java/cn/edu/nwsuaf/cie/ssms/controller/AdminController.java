package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.AdminService;
import cn.edu.nwsuaf.cie.ssms.service.CommonService;
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
    private AdminService adminService;

    @Autowired
    private UserHolder userHolder;

    @PostMapping("/login")
    public Result login(@RequestParam String uid, @RequestParam String authToken, HttpServletResponse response) {
        if (userHolder.getUser() != null && userHolder.getUser().getUid().equals(uid)) {
            return Result.success();
        }
        if (!UserCheck.isAdmin(uid)) {
            return Result.error(MsgCenter.ERROR_AUTH);
        }
        return LoginResultUtil.parseResult(CommonService.login(uid, authToken), response, 1);
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
