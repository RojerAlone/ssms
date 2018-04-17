package cn.edu.nwsuaf.cie.ssms.util;

import cn.edu.nwsuaf.cie.ssms.mapper.GroundMapper;
import cn.edu.nwsuaf.cie.ssms.model.Ground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangrenjie on 2018-04-17
 * 根据场地 id 获取场地的类型名字
 */
@Component
public class GroundUtil {

    private static Map<Integer, String> groundInfo;

    private static GroundMapper groundMapper;

    @Autowired
    public void setGroundMapper(GroundMapper mapper) {
        this.groundMapper = mapper;
    }

    @PostConstruct
    public void init() {
        List<Ground> grounds = groundMapper.selectAllInfo();
        for (Ground ground : grounds) {
            // 直接 substring(0, 3)，因为所有的名字都是前三位是某种场地，比如健美操室、羽毛球场
            groundInfo.put(ground.getId(), ground.getName().substring(0, 3));
        }
    }

    public static String getGroundTypeNameByGid(int gid) {
        return groundInfo.get(gid);
    }

}
