package cn.edu.nwsuaf.cie.ssms.interceptor;

import cn.edu.nwsuaf.cie.ssms.controller.AbstractController;
import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
@Component
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
        /*
          在 AbstractController 的 ThreadLocal 中存储了 HttpServletRequest 方便使用，
          但是 Spring 没有提供一个像 @ModelAttribute 这样的注解可以在每个 RequestMapping 方法执行完毕之后执行方法的注解
          （也就是说 @ModelAttribute 可以在每个请求方法执行之前执行 @ModelAttribute 注解的方法，但是没有一个对应的注解可以在
          每个请求方法执行之后执行注解的方法，所以只能放在拦截器中进行 ThreadLocal 的清除工作了。
         */
        AbstractController.remove();
    }
}
