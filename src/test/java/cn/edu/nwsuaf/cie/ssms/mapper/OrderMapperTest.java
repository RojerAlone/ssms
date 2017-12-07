package cn.edu.nwsuaf.cie.ssms.mapper;

import cn.edu.nwsuaf.cie.ssms.model.Order;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zhangrenjie on 2017-12-07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMapperTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMapperTest.class);

    @Autowired
    private OrderMapper orderMapper;

    private SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd hh:mm");

    @Before
    public void before() {
        System.out.println("\n");
    }

    @Test
    public void insert() throws Exception {
        int gid = 1;
        String uid = "2014012597";
        Date startTime = sdf.parse("2017-12-07 20:40");
        Date endTime = new Date(startTime.getTime() + TimeUtil.ONE_HOUR);
        int total = 10;
        Order order = new Order();
        order.setGid(gid);
        order.setUid(uid);
        order.setStartTime(startTime);
        order.setEndTime(endTime);
        order.setTotal(total);
        int res = orderMapper.insert(order);
        LOGGER.info("res : {}, order ID : {}", res, order.getId());
        assertTrue(res == 1);
    }

    @Test
    public void selectNumsBetweenTimeByGroundAndExcludeStat() throws Exception {
        int gid = 1;
        Date startTime = sdf.parse("2017-12-07 20:00");
        Date endTime = new Date(startTime.getTime() + TimeUtil.ONE_HOUR);
        int nums = orderMapper.selectNumsBetweenTimeByGroundAndExcludeStat(gid, startTime, endTime, Order.STAT_CANCEL);
        LOGGER.info("nums : {}", nums);
    }

}