package indi.zhifa.learn.xdclass.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * Description: 
 * @Copyright: Copyright (c) 2020-2028 北京华咨电力科技发展有限公司 All rights reserved.
 * @author: 褚智勇(hataksumo@163.com)
 * @date:  2022/2/10 10:42
 */
@EnableDiscoveryClient
@EnableFeignClients
@EnableOpenApi
@SpringBootApplication
public class GatewayApp {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApp.class, args);
    }
}
