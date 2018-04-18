package cn.edu.nwsuaf.cie.ssms.model;

import java.util.Date;

@Deprecated
public class LongOrder {

    public static final int STAT_OK = 0;

    public static final int STAT_DEL = 1;

    private Integer id;

    private Integer gid;

    private Date startDate;

    private Date endDate;

    private Date startTime;

    private Date endTime;

    private int weekday;

    private Integer stat;

    public LongOrder() {}

    public LongOrder(Integer gid, long startDate, long endDate, Long startTime, Long endTime, int weekday) {
        this.gid = gid;
        this.startDate = new Date(startDate);
        this.endDate = new Date(endDate);
        if (startTime != null) {
            this.startTime = new Date(startTime);
        }
        if (endTime != null) {
            this.endTime = new Date(endTime);
        }
        this.weekday = weekday;
    }

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public int getWeekday() {
        return weekday;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }

    public Integer getStat() {
        return stat;
    }

    public void setStat(Integer stat) {
        this.stat = stat;
    }

    @Override
    public String toString() {
        return "LongOrder{" +
                "id=" + id +
                ", gid=" + gid +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", weekday=" + weekday +
                ", stat=" + stat +
                '}';
    }
}