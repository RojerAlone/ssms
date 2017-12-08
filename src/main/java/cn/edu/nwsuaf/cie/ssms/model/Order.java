package cn.edu.nwsuaf.cie.ssms.model;

import java.util.Date;

public class Order {

    /**
     * 订单未支付，默认状态
     */
    public static final int STAT_NOT_PAY = 0;
    /**
     * 订单已支付
     */
    public static final int STAT_PAIED = 1;
    /**
     * 订单已取消
     */
    public static final int STAT_CANCEL = 2;

    private Integer id;

    private Integer gid;

    private String uid;

    private Date startTime;

    private Date endTime;

    private Integer total;

    private Integer payType;

    private Integer stat;

    private Date ctime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGid() {
        return gid;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", gid=" + gid +
                ", uid='" + uid + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", total=" + total +
                ", payType=" + payType +
                ", stat=" + stat +
                ", ctime=" + ctime +
                '}';
    }
}