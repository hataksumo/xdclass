package indi.zhifa.learn.xdclass.user.eneity.vo;

import lombok.Data;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;

@Data
public class LoginResponse{
    AuthUser user;
    String token;
}
