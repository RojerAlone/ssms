package cn.edu.nwsuaf.cie.ssms.model;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public class Result {

    private boolean success;
    private String msg;
    private Object result;

    private Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    private Result(boolean success, Object result) {
        this.success = success;
        this.result = result;
    }

    public static Result success(Object result) {
        return new Result(true, result);
    }

    public static Result error(String msg) {
        return new Result(false, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
