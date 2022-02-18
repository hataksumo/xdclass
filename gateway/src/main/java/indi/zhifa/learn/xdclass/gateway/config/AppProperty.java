package indi.zhifa.learn.xdclass.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Description: 
 * @Copyright: Copyright (c) 2020-2028 北京华咨电力科技发展有限公司 All rights reserved.
 * @author: 褚智勇(hataksumo@163.com)
 * @date:  2022/2/15 11:01
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperty {
    String a;
}
