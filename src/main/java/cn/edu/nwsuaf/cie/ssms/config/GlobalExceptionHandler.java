package cn.edu.nwsuaf.cie.ssms.config;

import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangrenjie on 2017-12-12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errorHandler(HttpServletRequest request, Exception e) {
        return Result.innerError();
    }

}
