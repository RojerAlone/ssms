package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by zhangrenjie on 2018-03-10
 */
public interface RootService {


    Result login(String username, String password);

    Result addAdmin(String uid);

    Result removeAdmin(String uid);

}
