package cn.edu.nwsuaf.cie.ssms;

import cn.edu.nwsuaf.cie.ssms.model.Access;
import cn.edu.nwsuaf.cie.ssms.model.User;
import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.Result;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
import cn.edu.nwsuaf.cie.ssms.util.UserHolder;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by zhangrenjie on 2017-12-05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserHolder userHolder;

    @Before
    public void before() {
        User user = new User();
        user.setUid("2014012586");
        user.setAccess(Access.ADMIN);
        userHolder.setUser(user);
    }

    @Test
    public void addTestDataOfOrderToday() throws Exception {
        System.out.println(System.currentTimeMillis());
        Random random = new Random();
        int gid = random.nextInt(12);
//        String date = TimeUtil.formatDate(DateUtils.addDays(new Date(),1 ));
        String date = TimeUtil.formatDate(new Date());
        String startTime = date + " 18:30";
        String endTime = date + " 20:30";
        Result result = orderService.order(gid, startTime, endTime);
//        if (!orderService.orderAndPay(gid, startTime, endTime).isSuccess()) {
        if (!result.isSuccess()) {
            System.out.println("插入数据失败 : " + result.getMsg());
        } else {
            System.out.println("插入成功");
        }
    }

    @Test
    public void addTestDataOfGymnasticsToday() throws Exception {
        int gid = 13;
        String date = TimeUtil.formatDate(new Date());
        if (!orderService.orderGymnastics(date, 1).isSuccess()) {
            System.out.println("插入数据失败");
        } else {
            System.out.println("插入成功");
        }
    }

}
