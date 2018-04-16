package cn.edu.nwsuaf.cie.ssms.util;

/**
 * Created by zhangrenjie on 2017-11-28
 * 消息字典，存放所有返回的消息
 */
public class MsgCenter {

    public static final String OK = "OK";

    public static final String SERVER_INNER_ERROR = "服务器内部错误，请重试";

    public static final String GROUND_ORDERED = "场地已经被预订，请选择其他场地";

    public static final String ERROR_PARAMS = "参数错误，请重试";

    public static final String MISS_PARAMS = "缺少参数: %s";

    public static final String ORDER_CANCEL_FAILED = "订单生效前 1 天内禁止取消订单";

    public static final String NOT_LOGIN = "您还没有登录，请登录后重试";

    public static final String ERROR_AUTH = "您没有对应的权限，请向管理员申请权限";

    public static final String ERROR_LOGIN = "用户名或密码错误，请重试";

    public static final String NOT_SUPPORT_METHOD = "不支持的 HTTP 请求方法: %s";

}
