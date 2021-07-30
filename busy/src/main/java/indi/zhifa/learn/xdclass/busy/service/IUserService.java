package indi.zhifa.learn.xdclass.busy.service;

import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;

/**
 * @author hatak
 */
public interface IUserService {
    AuthUser create(AuthUserDto pAuthUserDto);
    AuthUser checkUser(String pUserName, String pPasswd);
}
