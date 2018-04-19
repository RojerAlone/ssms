package cn.edu.nwsuaf.cie.ssms.model;

public class Ground {

    public static final int GYMNASTICS_ID = 13;

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