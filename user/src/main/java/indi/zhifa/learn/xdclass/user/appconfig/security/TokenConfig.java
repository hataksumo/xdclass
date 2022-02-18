package indi.zhifa.learn.xdclass.user.appconfig.security;

import lombok.Data;

@Data
public class TokenConfig {
    String secret;
    Integer duration;
}
