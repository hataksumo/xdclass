package indi.zhifa.learn.xdclass.busy.controller.init.bean;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hatak
 */
@Configuration
@RefreshScope
public class PasswdEncoderFactory {

    @Value("${security.passwd.strength}")
    int strength;

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(strength);
    }

}
