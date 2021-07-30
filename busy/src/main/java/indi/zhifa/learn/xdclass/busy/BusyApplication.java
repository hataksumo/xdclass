package indi.zhifa.learn.xdclass.busy;

import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author hatak
 */
@EnableDiscoveryClient
@MapperScans(value = {@MapperScan("indi.zhifa.learn.xdclass.busy.dao.mapper")})
@SpringBootApplication(scanBasePackages = {"indi.zhifa.learn.xdclass.busy"})
public class BusyApplication {
    public static void main(String[] args) {
        DtoEntityUtil.init();
        SpringApplication.run(BusyApplication.class, args);
    }
}
