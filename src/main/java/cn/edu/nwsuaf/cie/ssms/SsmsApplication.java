package cn.edu.nwsuaf.cie.ssms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "cn.edu.nwsuaf.cie.ssms.mapper") // 指定 mybatis 的 mapper 接口所在的位置
public class SsmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsmsApplication.class, args);
	}
}
