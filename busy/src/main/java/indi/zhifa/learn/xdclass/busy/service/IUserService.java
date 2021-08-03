package indi.zhifa.learn.xdclass.busy.service;

import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

/**
 * @author hatak
 */
public interface IUserService {
    AuthUser checkUser(String pUserName, String pPasswd);
    AuthUser createNew(RegisterAuthUserDto pUserDto);
    AuthUser editUser(Long pId,AuthUserDto pUserDto);
    AuthUser editSelfUser(AuthBaseUserInfo pUserDto);
    AuthUser changePasswd(Long pId,String pPasswd);
    boolean delete(Long pId);
    AuthUser info(Long pId);
}
