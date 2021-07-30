package indi.zhifa.learn.xdclass.busy.dao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;

/**
 * @author hatak
 */
public interface IAuthUserDaoService extends IService<AuthUser> {
    AuthUser findByName(String pName);
}
