package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.mapper.WorkerMapper;
import cn.edu.nwsuaf.cie.ssms.model.Access;
import cn.edu.nwsuaf.cie.ssms.model.Worker;
import cn.edu.nwsuaf.cie.ssms.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;

/**
 * @author RojerAlone
 * @Date 2017-12-11 22:33
 */
public class UserAccessService {

    @Autowired
    private static WorkerMapper workerMapper;

    @Value("#{root.username}")
    private static String ROOT;

    private static final Set<String> ADMINS = new HashSet<>();

    private static final Set<String> WORKERS = new HashSet<>();

    static {
        List<Worker> workers = workerMapper.getAll();
        for (Worker worker : workers) {
            if (worker.getType() == Worker.ADMIN) {
                ADMINS.add(worker.getUid());
            }
            if (worker.getType() == Worker.WORKER) {
                WORKERS.add(worker.getUid());
            }
        }
    }

    public static boolean check(String uid) {
        return uid.length() == 10;
    }

    public static void addAdmin(String... uids) {
        ADMINS.addAll(Arrays.asList(uids));
        for (String uid : uids) {
            workerMapper.insert(uid, Worker.ADMIN);
        }
    }

    public static void addWorker(String... uids) {
        WORKERS.addAll(Arrays.asList(uids));
        for (String uid : uids) {
            workerMapper.insert(uid, Worker.WORKER);
        }
    }

    public static void removeAdmin(String uid) {
        ADMINS.remove(uid);
        workerMapper.delete(uid, Worker.ADMIN);
    }

    public static void removeWorker(String uid) {
        WORKERS.remove(uid);
        workerMapper.delete(uid, Worker.WORKER);
    }

    public static List<String> getAdmin(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        return new ArrayList<>(ADMINS).subList(pageInfo[0], pageInfo[0] + pageInfo[1]);
    }

    public static List<String> getWorker(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        return new ArrayList<>(WORKERS).subList(pageInfo[0], pageInfo[0] + pageInfo[1]);
    }

    public static boolean isAdmin(String uid) {
        return ADMINS.contains(uid);
    }

    public static boolean isWorker(String uid) {
        return WORKERS.contains(uid);
    }

    public static Access getAccess(String uid) {
        if (ROOT.equals(uid)) {
            return Access.ROOT;
        }
        if (isAdmin(uid)) {
            return Access.ADMIN;
        }
        if (isWorker(uid)) {
            return Access.WORKER;
        }
        return Access.NORMAL;
    }
}
