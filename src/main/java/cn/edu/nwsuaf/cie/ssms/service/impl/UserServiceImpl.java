package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.service.UserService;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
import cn.edu.nwsuaf.cie.ssms.util.UserCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by zhangrenjie on 2017-11-29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CommonCache cache;

    @Override
    public Result login(String uid, String authToken) {
        // TODO 判断合法的 authToken
        if (!UserCheck.check(uid)) {
            return Result.errorParam();
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        User user = new User();
        user.setUid(uid);
        cache.put(token, user);
        return Result.success(token);
    }

    @Override
    public Result logout(String token) {
        cache.remove(token);
        return Result.success();
    }

}
