package cn.edu.nwsuaf.cie.ssms.util;

import cn.edu.nwsuaf.cie.ssms.model.User;

/**
 * Created by zhangrenjie on 2017-11-28
 */
public class UserHolder {

    private static final ThreadLocal<User> USERS = new ThreadLocal<User>();

    public User getUser() {
        return USERS.get();
    }

    public void setUser(User user) {
        USERS.set(user);
    }

    public void remove() {
        USERS.remove();
    }

}
