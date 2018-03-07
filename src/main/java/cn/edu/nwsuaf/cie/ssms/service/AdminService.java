package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserCheck;
import org.springframework.stereotype.Service;

/**
 * @author RojerAlone
 * @Date 2017-12-13 23:33
 */
@Service
public class AdminService {

    public Result addWorker(String uid) {
        if (!UserCheck.check(uid)) {
            return Result.errorParam();
        }
        UserCheck.addSpecialUser(uid);
        return Result.success();
    }

    public Result removeWorker(String uid) {
        UserCheck.removeSpecialUser(uid);
        return Result.success();
    }

}
