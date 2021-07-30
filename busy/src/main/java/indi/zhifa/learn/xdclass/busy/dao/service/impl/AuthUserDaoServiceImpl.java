package indi.zhifa.learn.xdclass.busy.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zhifa.learn.xdclass.busy.dao.mapper.AuthUserMapper;
import indi.zhifa.learn.xdclass.busy.dao.service.IAuthUserDaoService;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;

/**
 * @author hatak
 */
public class AuthUserDaoServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserDaoService {
    @Override
    public AuthUser findByName(String pName) {
        LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.<AuthUser>lambdaQuery()
                .eq(AuthUser::getName,pName);
        return baseMapper.selectOne(queryWrapper);
    }
}
