package indi.zhifa.learn.xdclass.busy.eneity.vo;

import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import lombok.Data;

@Data
public class LoginResponse{
    AuthUser user;
    String token;
}
