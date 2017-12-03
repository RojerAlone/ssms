package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by zhangrenjie on 2017-11-29
 */
public interface UserService {

    /**
     * 用户登录，前端用账号密码向登录服务请求后返回令牌，再向这里请求获取这里的令牌
     * @param uid 学工号
     * @param authToken 身份令牌
     * @return
     */
    Result login(String uid, String authToken);

    Result logout(String token);

}
