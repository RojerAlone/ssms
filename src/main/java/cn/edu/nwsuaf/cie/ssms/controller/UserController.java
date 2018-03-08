package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.util.LoginResultUtil;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangrenjie on 2017-11-29
 */
@RestController
@RequestMapping(value = "user")
public class UserController extends AbstractController {

    @Autowired
    private UserHolder userHolder;

    @PostMapping("login")
    public Result login(@RequestParam String uid, @RequestParam String authToken, HttpServletResponse response) {
        if (userHolder.getUser() != null && userHolder.getUser().getUid().equals(uid)) {
            return Result.success();
        }
        return LoginResultUtil.parseResult(CommonService.login(uid, authToken), response, 7);
    }

}
