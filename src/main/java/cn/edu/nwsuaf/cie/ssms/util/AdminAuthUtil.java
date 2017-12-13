package cn.edu.nwsuaf.cie.ssms.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;

/**
 * Created by zhangrenjie on 2017-12-13
 */
public class AdminAuthUtil {

    private static String SALT = "666qwe,.";

    @Value("#{admin.uid}")
    private static String UID;

    @Value("#{admin.pswd}")
    private static String PSWD;

    public static boolean auth(String uid, String password) {
        return UID.equals(uid) && PSWD.equals(DigestUtils.md5DigestAsHex((password + SALT).getBytes()));
    }

}
