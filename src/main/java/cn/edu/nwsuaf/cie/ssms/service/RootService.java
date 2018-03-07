package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.RootAuthUtil;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserCheck;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by zhangrenjie on 2018-03-04
 */
@Service
public class RootService {

    public Result login(String username, String password) {
        if (RootAuthUtil.auth(username, password)) {
            String token = UUID.randomUUID().toString().replace("-", "");
            return CommonService.login(username, token);
        }
        return Result.error(MsgCenter.ERROR_LOGIN);
    }

    public Result addAdmin(String uid) {
        if (!UserCheck.check(uid)) {
            return Result.errorParam();
        }
        UserCheck.addAdmin(uid);
        return Result.success();
    }

    public Result removeAdmin(String uid) {
        UserCheck.removeAdmin(uid);
        return Result.success();
    }

}
