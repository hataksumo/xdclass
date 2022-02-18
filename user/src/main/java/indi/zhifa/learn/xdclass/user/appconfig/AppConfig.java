package indi.zhifa.learn.xdclass.user.appconfig;

import indi.zhifa.learn.xdclass.user.appconfig.security.SecurityWordConfig;
import indi.zhifa.learn.xdclass.user.appconfig.user.UserConfig;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    SecurityWordConfig security;
    UserConfig user;
}
