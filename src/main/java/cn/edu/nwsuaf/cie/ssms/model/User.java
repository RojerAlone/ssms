package cn.edu.nwsuaf.cie.ssms.model;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public class User {

    private String uid;

    /**
     * 是否是学生
     */
    private boolean student;

    /**
     * 用户的权限
     */
    private Access access;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public Access getAccess() {
        return access;
    }

    public void setAccess(Access access) {
        this.access = access;
    }
}
