package cn.edu.nwsuaf.cie.ssms.util;

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

    public static Result success() {
        return new Result(true, MsgCenter.OK);
    }

    public static Result error(String msg) {
        return new Result(false, msg);
    }

    public static Result innerError() {
        return new Result(false, MsgCenter.SERVER_INNER_ERROR);
    }

    public static Result errorParam() {
        return Result.error(MsgCenter.ERROR_PARAMS);
    }

    public static Result missParam(String paramName) {
        return Result.error(String.format(MsgCenter.MISS_PARAMS, paramName));
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

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                ", result=" + result +
                '}';
    }
}
