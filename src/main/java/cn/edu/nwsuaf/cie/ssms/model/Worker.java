package cn.edu.nwsuaf.cie.ssms.model;

/**
 * Created by zhangrenjie on 2018-03-10
 */
public class Worker {

    public static final int WORKER = 0;
    public static final int ADMIN = 1;

    private String uid;
    private int type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
