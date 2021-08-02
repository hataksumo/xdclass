package indi.zhifa.learn.xdclass.busy.service.impl;

import indi.zhifa.learn.xdclass.busy.dao.service.IAuthUserDaoService;
import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.busy.service.IUserService;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author hatak
 */
@Log4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    final PasswordEncoder passwordEncoder;
    final IAuthUserDaoService mAuthUserDaoService;

    @Override
    public AuthUser create(RegisterAuthUserDto pRegisterAuthUserDto) {

        AuthUser authUser = mAuthUserDaoService.findByName(pRegisterAuthUserDto.getName());
        if(null != authUser){
            throw new ServiceException("用户名["+ pRegisterAuthUserDto.getName()+"]已注册");
        }
        authUser = DtoEntityUtil.dtoToPo(pRegisterAuthUserDto,AuthUser.class);
        authUser.setPasswd(passwordEncoder.encode(pRegisterAuthUserDto.getPasswd()));
        mAuthUserDaoService.save(authUser);
        return authUser;
    }

    @Override
    public AuthUser checkUser(String pUserName, String pPasswd) {
        AuthUser authUser = mAuthUserDaoService.findByName(pUserName);
        if(null == authUser){
            throw new ServiceException("没有找到用户名为["+pUserName+"]的用户");
        }
        String passwd = passwordEncoder.encode(pPasswd);
        if(authUser.getPasswd().equals(passwd)){
            return authUser;
        }else{
            throw new ServiceException("密码错误");
        }
    }
}
