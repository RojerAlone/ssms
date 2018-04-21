package cn.edu.nwsuaf.cie.ssms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by zhangrenjie on 2017-12-01
 */
@Component
public class Price {

    public static int STUDENT_PRICE;

    public static int TEACHER_PRICE;

    @Value("${properties.price.student}")
    private void setStudentPrice(int studentPrice) {
        STUDENT_PRICE = studentPrice;
    }

    @Value("${properties.price.teacher}")
    private void setTeacherPrice(int teacherPrice) {
        TEACHER_PRICE = teacherPrice;
    }
}
