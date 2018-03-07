package cn.edu.nwsuaf.cie.ssms.model;

/**
 * Created by zhangrenjie on 2018-03-07
 */
public enum Access {

    /**
     * 用户权限，分为 root、管理员、工作人员和普通用户
     */
    ROOT("root"),
    ADMIN("admin"),
    WORKER("worker"),
    NORMAL("normal");

    Access(String access) {

    }

}
