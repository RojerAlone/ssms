package cn.edu.nwsuaf.cie.ssms.model;

public class Ground {

    public static final int GYMNASTICS_ID = 13;

    /**
     * 健美操室不分半个小时预订，根据两个时间段预订，课余时间和晚自习时间，默认课余时间是 00:00 开始，晚自习时间为 20:00 开始
     */
    public static final String GYMNASTICS_REST_TIME = "00:00";
    public static final String GYMNASTICS_NIGHT_TIME = "20:00";

    private Integer id;

    private String name;

    private Integer type;

    private boolean used;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed() {
        this.used = true;
    }
}