package cn.edu.nwsuaf.cie.ssms.config;

import cn.edu.nwsuaf.cie.ssms.interceptor.AdminInterceptor;
import cn.edu.nwsuaf.cie.ssms.interceptor.AuthInterceptor;
import cn.edu.nwsuaf.cie.ssms.interceptor.LoginNeedInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private LoginNeedInterceptor loginNeedInterceptor;
    @Autowired
    private AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(loginNeedInterceptor).addPathPatterns("/order/**");
//        registry.addInterceptor(adminInterceptor).addPathPatterns("/closeinfo/**", "/admin/**", "/root/**");
        super.addInterceptors(registry);
    }
}
