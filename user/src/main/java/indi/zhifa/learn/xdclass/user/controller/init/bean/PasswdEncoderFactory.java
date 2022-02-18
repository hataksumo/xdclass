package indi.zhifa.learn.xdclass.user.controller.init.bean;


import indi.zhifa.learn.xdclass.user.appconfig.AppConfig;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author hatak
 */
@Configuration
@AllArgsConstructor
public class PasswdEncoderFactory {

    final AppConfig appConfig;

    @Bean
    PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(appConfig.getSecurity().getPasswd().getStrength());
    }

}
