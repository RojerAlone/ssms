package cn.edu.nwsuaf.cie.ssms.util;

import cn.edu.nwsuaf.cie.ssms.mapper.WorkerMapper;
import cn.edu.nwsuaf.cie.ssms.model.Access;
import cn.edu.nwsuaf.cie.ssms.model.Worker;
import cn.edu.nwsuaf.cie.ssms.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author RojerAlone
 * @Date 2017-12-11 22:33
 */
@Component
public class UserAccessUtil {

    private static WorkerMapper workerMapper;

    private static String ROOT;

    private static final Set<String> ADMINS = new HashSet<>();

    private static final Set<String> WORKERS = new HashSet<>();

    @Value("${root.username}")
    public void setROOT(String root) {
        ROOT = root;
    }

    @Autowired
    public void setWorkerMapper(WorkerMapper mapper) {
        workerMapper = mapper;
    }

    @PostConstruct
    public void init() {
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
        int size = ADMINS.size();
        int startPos = pageInfo[0];
        if (startPos >= size) {
            return new ArrayList<>(0);
        }
        int endPos = pageInfo[0] + pageInfo[1];
        if (endPos > size) {
            endPos = size;
        }
        return new ArrayList<>(ADMINS).subList(startPos, endPos);
    }

    public static List<String> getWorker(int page, int nums) {
        int[] pageInfo = PageUtil.getPage(page, nums);
        int size = WORKERS.size();
        int startPos = pageInfo[0];
        if (startPos >= size) {
            return new ArrayList<>(0);
        }
        int endPos = pageInfo[0] + pageInfo[1];
        if (endPos > size) {
            endPos = size;
        }
        return new ArrayList<>(WORKERS).subList(startPos, endPos);
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
