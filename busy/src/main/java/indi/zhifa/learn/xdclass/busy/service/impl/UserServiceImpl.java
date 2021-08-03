package indi.zhifa.learn.xdclass.busy.service.impl;

import indi.zhifa.learn.xdclass.busy.dao.service.IAuthUserDaoService;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.busy.service.IUserService;
import indi.zhifa.learn.xdclass.busy.util.TokenUtil;
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
    final TokenUtil tokenUtil;

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

    @Override
    public AuthUser createNew(RegisterAuthUserDto pUserDto) {
        AuthUser authUser = mAuthUserDaoService.findByName(pUserDto.getName());
        if(null != authUser){
            throw new ServiceException("用户名["+ pUserDto.getName()+"]已注册");
        }
        authUser = DtoEntityUtil.dtoToPo(pUserDto,AuthUser.class);
        authUser.setPasswd(passwordEncoder.encode(pUserDto.getPasswd()));
        mAuthUserDaoService.save(authUser);
        return authUser;
    }

    @Override
    public AuthUser editUser(Long pId, AuthUserDto pUserDto) {
        AuthUser authUser = mAuthUserDaoService.getById(pId);
        if(null == authUser){
            throw new ServiceException("没有找到id为["+pId+"]的用户");
        }
        DtoEntityUtil.copy(authUser,pUserDto);
        mAuthUserDaoService.save(authUser);
        return authUser;
    }

    @Override
    public AuthUser editSelfUser(AuthBaseUserInfo pUserDto) {
        TokenObject tokenObject = tokenUtil.getTokenObject();
        AuthUser authUser = checkUser(tokenObject.getId());
        DtoEntityUtil.copy(authUser,pUserDto);
        mAuthUserDaoService.save(authUser);
        return authUser;
    }

    @Override
    public AuthUser changePasswd(Long pId, String pPasswd) {
        AuthUser authUser = checkUser(pId);
        String passwd = passwordEncoder.encode(pPasswd);
        authUser.setPasswd(passwd);
        mAuthUserDaoService.save(authUser);
        return authUser;
    }

    @Override
    public boolean delete(Long pId) {
        boolean res = mAuthUserDaoService.removeById(pId);
        return res;
    }

    @Override
    public AuthUser info(Long pId) {
        AuthUser authUser = checkUser(pId);
        return authUser;
    }

    protected AuthUser checkUser(Long pId){
        AuthUser authUser = mAuthUserDaoService.getById(pId);
        if(null == authUser){
            throw new ServiceException("没有找到id为["+pId+"]的用户");
        }
        return authUser;
    }
}
