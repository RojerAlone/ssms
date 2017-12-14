package cn.edu.nwsuaf.cie.ssms.model;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public class User {

    private String uid;

    private boolean student;

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
}
