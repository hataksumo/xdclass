package indi.zhifa.learn.xdclass.user.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;

/**
 * @author hatak
 */
public interface IAuthUserDaoService extends IService<AuthUser> {
    AuthUser findByName(String pName);
}
