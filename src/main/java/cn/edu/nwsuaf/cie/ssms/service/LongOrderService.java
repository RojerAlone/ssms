package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.util.Result;

/**
 * Created by RojerAlone on 2017-12-05.
 */
@Deprecated
public interface LongOrderService {

    Result order(LongOrder longOrder);

    Result delete(int id);

    Result getAll(int page, int nums);

}
