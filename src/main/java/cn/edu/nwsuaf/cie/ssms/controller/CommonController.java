package cn.edu.nwsuaf.cie.ssms.controller;

import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author RojerAlone
 * @Date 2017-12-11 23:08
 */
@RestController
public class CommonController {

    @RequestMapping("/notLogin")
    public Result notLogin() {
        return Result.error(MsgCenter.NOT_LOGIN);
    }

    @RequestMapping("/notAdmin")
    public Result notAdmin() {
        return Result.error(MsgCenter.ERROR_AUTH);
    }

}
