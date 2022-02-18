package indi.zhifa.learn.xdclass.user;

import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hatak
 */
@EnableDiscoveryClient
@MapperScans(value = {@MapperScan("indi.zhifa.learn.xdclass.user.dao.mapper")})
@SpringBootApplication(scanBasePackages = {"indi.zhifa.learn.xdclass.user","indi.zhifa.learn.xdclass.common"})
public class UserApplication {
    public static void main(String[] args) {
        DtoEntityUtil.init();
        SpringApplication.run(UserApplication.class, args);
    }
}
