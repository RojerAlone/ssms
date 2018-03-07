package cn.edu.nwsuaf.cie.ssms.util;

import cn.edu.nwsuaf.cie.ssms.model.Access;
import org.springframework.beans.factory.annotation.Value;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author RojerAlone
 * @Date 2017-12-11 22:33
 */
public class UserCheck {

    @Value("#{root.username}")
    private static String ROOT;

    private static final Set<String> ADMINS = new HashSet<>();

    private static final Set<String> SPECIAL_USER = new HashSet<>();

    public static boolean check(String uid) {
        return uid.length() == 10;
    }

    public static void addAdmin(String... uid) {
        ADMINS.addAll(Arrays.asList(uid));
    }

    public static void addSpecialUser(String... uid) {
        SPECIAL_USER.addAll(Arrays.asList(uid));
    }

    public static void removeAdmin(String uid) {
        ADMINS.remove(uid);
    }

    public static void removeSpecialUser(String uid) {
        SPECIAL_USER.remove(uid);
    }

    public static boolean isAdmin(String uid) {
        return ADMINS.contains(uid);
    }

    public static boolean isSpecial(String uid) {
        return SPECIAL_USER.contains(uid);
    }

    public static Access getAccess(String uid) {
        if (ROOT.equals(uid)) {
            return Access.ROOT;
        }
        if (isAdmin(uid)) {
            return Access.ADMIN;
        }
        if (isSpecial(uid)) {
            return Access.WORKER;
        }
        return Access.NORMAL;
    }
}
