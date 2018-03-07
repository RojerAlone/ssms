package cn.edu.nwsuaf.cie.ssms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

/**
 * Created by zhangrenjie on 2017-12-13
 */
public class RootAuthUtil {

    private static String SALT = "666qwe,.";

    @Value("#{root.username}")
    private static String USERNAME;

    @Value("#{root.pswd}")
    private static String PSWD;

    public static boolean auth(String username, String password) {
        return USERNAME.equals(username) && PSWD.equals(pswdGenerator(password));
    }

    private static String pswdGenerator(String password) {
        return DigestUtils.md5DigestAsHex((password + SALT).getBytes());
    }

    public static void main(String[] args) {
        String password = "test";
        System.out.println(pswdGenerator(password));
    }

}
