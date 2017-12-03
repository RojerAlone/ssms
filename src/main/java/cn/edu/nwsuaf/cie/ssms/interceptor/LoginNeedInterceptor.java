package cn.edu.nwsuaf.cie.ssms.interceptor;

import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhangrenjie on 2017-12-03
 * 权限拦截，拦截只有登录了的用户才能进行的操作
 */
@Component
public class LoginNeedInterceptor implements HandlerInterceptor {

    @Autowired
    private UserHolder userHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return userHolder.getUser() != null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
