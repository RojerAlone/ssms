package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.service.AdminService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserAccessUtil;
import org.springframework.stereotype.Service;

/**
 * @author RojerAlone
 * @Date 2017-12-13 23:33
 */
@Service
public class AdminServiceImpl implements AdminService {

    public Result addWorker(String uid) {
        if (!UserAccessUtil.check(uid)) {
            return Result.errorParam();
        }
        UserAccessUtil.addWorker(uid);
        return Result.success();
    }

    public Result removeWorker(String uid) {
        UserAccessUtil.removeWorker(uid);
        return Result.success();
    }

}
