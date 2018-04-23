package cn.edu.nwsuaf.cie.ssms.model;

import java.util.Date;

/**
 * Created by zhangrenjie on 2018-04-23
 */
public class Message {

    public static final int STAT_OK = 0;
    public static final int STAT_DEL = 1;

    private int id;
    private String uid;
    private String title;
    private String content;
    private Date ctime;
    private int stat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public int getStat() {
        return stat;
    }

    public void setStat(int stat) {
        this.stat = stat;
    }
}
