package indi.zhifa.learn.xdclass.busy.appconfig.security;

import lombok.Data;

@Data
public class TokenConfig {
    String secret;
    Integer duration;
}
