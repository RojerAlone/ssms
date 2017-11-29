package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.model.Result;
import cn.edu.nwsuaf.cie.ssms.service.UserService;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangrenjie on 2017-11-29
 */
@RestController
@RequestMapping(value = "user")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserHolder userHolder;

    @PostMapping("login")
    public Result login(String uid, String authToken, HttpServletResponse response) {
        if (userHolder.getUser() != null && userHolder.getUser().getUid().equals(uid)) {
            return Result.success();
        }
        // TODO 判断是否是合法的登录
        Result result = userService.login(uid, authToken);
        if (result.isSuccess()) {
            Cookie cookie = new Cookie("token", (String) result.getResult());
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7); // 设置 cookie 有效期
            response.addCookie(cookie);
            return Result.success();
        } else {
            return result;
        }
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
        return userService.logout(token);
    }

}
