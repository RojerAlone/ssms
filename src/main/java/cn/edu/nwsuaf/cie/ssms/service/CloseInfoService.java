package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by RojerAlone on 2017-12-05.
 */
public interface CloseInfoService {

    Result close(CloseInfo closeInfo);

    Result delete(int id);

    Result getAll(int page, int nums);

}
