package cn.edu.nwsuaf.cie.ssms.service.impl;

import cn.edu.nwsuaf.cie.ssms.model.Result;
import cn.edu.nwsuaf.cie.ssms.service.UserService;
import cn.edu.nwsuaf.cie.ssms.util.CommonCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhangrenjie on 2017-11-29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private CommonCache cache;

    @Override
    public Result login(String uid, String authToken) {
        return null;
    }

    @Override
    public Result logout(String token) {
        cache.remove(token);
        return Result.success();
    }

}
