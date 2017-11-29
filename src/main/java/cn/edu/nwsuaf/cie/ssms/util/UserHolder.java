package cn.edu.nwsuaf.cie.ssms.util;

import cn.edu.nwsuaf.cie.ssms.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by zhangrenjie on 2017-11-28
 */
@Component
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
