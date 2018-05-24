package cn.edu.nwsuaf.cie.ssms.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangrenjie on 2018-03-08
 */
public class LoginResultUtil {

    public static Result parseResult(String uid, Result result, HttpServletResponse response, int days) {
        if (result.isSuccess()) {
            Cookie cookie = new Cookie("token", (String) result.getResult());
            cookie.setPath("/");
            cookie.setMaxAge((int) (TimeUtil.ONE_DAY * days / 1000)); // 设置 cookie 有效期
            response.addCookie(cookie);
            return Result.success(UserAccessUtil.getAccess(uid).name());
        } else {
            return result;
        }
    }

}
