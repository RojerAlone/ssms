package cn.edu.nwsuaf.cie.ssms.interceptor;

import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangrenjie on 2017-11-28
 * 用户身份验证拦截器，从 cookie 中获取 token，如果用户是已经登录的，那么将用户信息保存在 UserHolder 中
 * 请求结束后要将用户信息从 UserHolder 中删除
 */
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private UserHolder userHolder;
    @Autowired
    private CommonCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null) { // 如果有 token，获取用户信息
            User user = (User) cache.get(token);
            if (user != null) {
                userHolder.setUser(user);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        userHolder.remove();
    }
}
