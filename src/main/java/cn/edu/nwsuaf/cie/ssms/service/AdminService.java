package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.AdminAuthUtil;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserCheck;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author RojerAlone
 * @Date 2017-12-13 23:33
 */
@Service
public class AdminService {

    public Result login(String uid, String password) {
        if (AdminAuthUtil.auth(uid, password)) {
            String token = UUID.randomUUID().toString().replace("-", "");
            return Result.success(token);
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

    public Result addSpecialUser(String uid) {
        if (!UserCheck.check(uid)) {
            return Result.errorParam();
        }
        UserCheck.addSpecialUser(uid);
        return Result.success();
    }

    public Result removeSpecialUser(String uid) {
        UserCheck.removeSpecialUser(uid);
        return Result.success();
    }

}
