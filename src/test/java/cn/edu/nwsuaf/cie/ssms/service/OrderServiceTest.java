package cn.edu.nwsuaf.cie.ssms.service;

import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhangrenjie on 2017-12-03
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@SpringBootConfiguration
//@MybatisTest
public class OrderServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceTest.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserHolder userHolder;

    @Before
    public void initTest() {
        User user = new User();
        user.setStudent(true);
        user.setUid("2014012597");
        userHolder.setUser(user);
    }

    @Test
    public void order() throws Exception {
        int gid = 1;
        String uid = "2014012597";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date startDate = sdf.parse("2017-12-03 14:00");
        Date endTime = sdf.parse("2017-12-03 16:00");
        Result result = orderService.order(uid, gid, startDate, endTime);
        LOGGER.debug("order : {}", result);
        assert(result.isSuccess());
    }

}