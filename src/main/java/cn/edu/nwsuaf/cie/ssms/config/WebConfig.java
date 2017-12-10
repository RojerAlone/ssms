package cn.edu.nwsuaf.cie.ssms.config;

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
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private AuthInterceptor authInterceptor;
    @Autowired
    private LoginNeedInterceptor loginNeedInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**");
//        registry.addInterceptor(loginNeedInterceptor).addPathPatterns("/order/**");
        super.addInterceptors(registry);
    }
}
