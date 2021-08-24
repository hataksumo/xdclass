package indi.zhifa.learn.xdclass.busy.appconfig.security;

import lombok.Data;

@Data
public class SecurityWordConfig {
    TokenConfig token;
    PasswordConfig passwd;
    VerifyCodeConfig verifyCode;
}
