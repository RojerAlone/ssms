package cn.edu.nwsuaf.cie.ssms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * Created by zhangrenjie on 2017-12-13
 */
@Component
public class RootAuthUtil {

    private static String SALT = "666qwe,.";

    private static String USERNAME;

    private static String PSWD;

    @Value("${root.username}")
    private void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    @Value("${root.pswd}")
    private void setPSWD(String PSWD) {
        this.PSWD = PSWD;
    }

    public static boolean auth(String username, String password) {
        return USERNAME.equals(username) && PSWD.equals(pswdGenerator(password));
    }

    private static String pswdGenerator(String password) {
        // 使用 Spring Framework 提供的 DigestUtils 进行 MD5 加密
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes());
    }

    public static void main(String[] args) {
        String password = "123456";
        System.out.println(pswdGenerator(password));
    }

}
