package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.service.CommonService;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

/**
 * @author RojerAlone
 * @Date 2017-12-11 23:08
 */
@RestController
public class CommonController extends AbstractController {

    @Autowired
    private UserHolder userHolder;

    @RequestMapping("/notLogin")
    public Result notLogin() {
        return Result.error(MsgCenter.NOT_LOGIN);
    }

    @RequestMapping("/notAdmin")
    public Result notAdmin() {
        return Result.error(MsgCenter.ERROR_AUTH);
    }

    @PostMapping("logout")
    public Result logout() {
        String token = null;
        if (getRequest().getCookies() != null) {
            for (Cookie cookie : getRequest().getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            return Result.errorParam();
        }
        return CommonService.logout(userHolder.getUser().getUid(), token);
    }


}
