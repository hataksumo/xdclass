package indi.zhifa.learn.xdclass.busy.appconfig;

import indi.zhifa.learn.xdclass.busy.appconfig.security.SecurityWordConfig;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    SecurityWordConfig security;
}
