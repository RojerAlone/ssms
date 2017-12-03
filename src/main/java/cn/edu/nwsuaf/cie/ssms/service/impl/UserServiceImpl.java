package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.util.MsgCenter;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.service.UserService;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
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
        if (uid.length() != 10) {
            return Result.error(MsgCenter.ERROR_PARAMS);
        }
        return Result.success(UUID.randomUUID().toString().replaceAll("-", ""));
    }

    @Override
    public Result logout(String token) {
        cache.remove(token);
        return Result.success();
    }

}
