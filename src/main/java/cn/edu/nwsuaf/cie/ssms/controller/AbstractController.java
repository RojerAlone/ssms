package cn.edu.nwsuaf.cie.ssms.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangrenjie on 2017-11-29
 */
public abstract class AbstractController {

    private static ThreadLocal<HttpServletRequest> httpServletRequestThreadLocal = new ThreadLocal<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

    /**
     * 使用 @ModelAttribute 注解标识的方法会在每个控制器中的方法访问之前先调用
     * 但是没有对应的注解可以在方法执行之后执行，所以只能把 ThreadLocal 的清除工作放在了拦截器中
     * @param request
     */
    @ModelAttribute
    protected void setThreadLocal(HttpServletRequest request) {
        httpServletRequestThreadLocal.set(request);
    }

    protected HttpServletRequest getRequest() {
        return httpServletRequestThreadLocal.get();
    }

    public static void remove() {
        httpServletRequestThreadLocal.remove();
    }

}
