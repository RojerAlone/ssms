package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.AdminAuthUtil;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
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

}
