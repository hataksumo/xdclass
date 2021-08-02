package indi.zhifa.learn.xdclass.busy.service;

import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;

/**
 * @author hatak
 */
public interface IUserService {
    AuthUser create(RegisterAuthUserDto pRegisterAuthUserDto);
    AuthUser checkUser(String pUserName, String pPasswd);
}
