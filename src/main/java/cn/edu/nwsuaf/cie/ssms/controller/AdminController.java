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

    @PutMapping("/worker/{uid}")
    public Result addWorker(@PathVariable("uid") String uid) {
        return adminService.addWorker(uid);
    }

    @DeleteMapping("/worker/{uid}")
    public Result removeWorker(@PathVariable("uid") String uid) {
        return adminService.removeWorker(uid);
    }

}
