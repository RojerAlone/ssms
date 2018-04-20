package cn.edu.nwsuaf.cie.ssms;

import cn.edu.nwsuaf.cie.ssms.service.OrderService;
import cn.edu.nwsuaf.cie.ssms.util.TimeUtil;
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

    @Test
    public void addTestDataOfOrderToday() throws Exception {
        System.out.println(System.currentTimeMillis());
        Random random = new Random();
        int gid = random.nextInt(12);
        String date = TimeUtil.formatDate(new Date());
        String startTime = date + " 16:30";
        String endTime = date + " 18:30";
        if (!orderService.orderAndPay(gid, startTime, endTime).isSuccess()) {
            System.out.println("插入数据失败");
        } else {
            System.out.println("插入成功");
        }
    }

}
