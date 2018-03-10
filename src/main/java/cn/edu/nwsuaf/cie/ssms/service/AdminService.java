package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by zhangrenjie on 2018-03-10
 */
public interface AdminService {

    Result addWorker(String uid);

    Result removeWorker(String uid);

}
