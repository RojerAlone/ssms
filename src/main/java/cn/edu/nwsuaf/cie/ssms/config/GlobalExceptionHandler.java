package cn.edu.nwsuaf.cie.ssms.config;

import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangrenjie on 2017-12-12
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result errorHandler(HttpServletRequest request, Exception e) {
        LOGGER.error("inner error", e);
        return Result.innerError();
    }

}
