package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.LongOrder;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangrenjie on 2017-12-08
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LongOrderMapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseInfoMapperTest.class);

    @Autowired
    private LongOrderMapper longOrderMapper;

    @Before
    public void before() {
        System.out.println("\n");
    }


    @Test
    public void insert() throws Exception {
        Date startTime = TimeUtil.parseDateTime("2017-12-07 20:40:30");
        Date endTime = TimeUtil.parseDateTime("2018-12-07 20:40:30");
        LongOrder longOrder = new LongOrder();
        longOrder.setGid(1);
        longOrder.setStartDate(startTime);
        longOrder.setEndDate(endTime);
        longOrder.setStartTime(startTime);
        longOrder.setEndTime(endTime);
        longOrder.setWeekday(4);
        int result = longOrderMapper.insert(longOrder);
        LOGGER.info("result : {}", result);
        assertTrue(result == 1);
    }

    @Test
    public void selectByGidAndStatAndDate() throws Exception {
        String timeStr = "2017-12-07 21:40:30";
        System.out.println(DateFormat.getTimeInstance().parse(timeStr));
        List<LongOrder> longOrders = longOrderMapper.selectByGidAndStatAndDate(1, LongOrder.STAT_OK,
                TimeUtil.parseDateTime(timeStr));
        LOGGER.info("results : {}", longOrders);
        assertTrue(!longOrders.isEmpty());
    }

}