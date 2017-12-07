package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.CloseInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by zhangrenjie on 2017-12-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CloseInfoMapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseInfoMapperTest.class);

    @Autowired
    private CloseInfoMapper closeInfoMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Before
    public void before() {
        System.out.println("\n");
    }

    @Test
    public void insert() throws Exception {
        Date startTime = sdf.parse("2017-12-07 20:40:30");
        CloseInfo closeInfo = new CloseInfo();
        closeInfo.setGid(1);
        closeInfo.setStartTime(startTime);
        closeInfo.setCloseDate(startTime);
//        closeInfo.setCloseDate(sdf.parse("2017-12-07"));
        int res = closeInfoMapper.insert(closeInfo);
        LOGGER.info("result : {}", res);
        assertTrue(res == 1);
    }

    @Test
    public void selectByGidAndStatAndCloseDate() throws Exception {
    }

}