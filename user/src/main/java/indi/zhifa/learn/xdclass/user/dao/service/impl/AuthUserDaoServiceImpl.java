package indi.zhifa.learn.xdclass.user.dao.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zhifa.learn.xdclass.user.dao.mapper.AuthUserMapper;
import indi.zhifa.learn.xdclass.user.dao.service.IAuthUserDaoService;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;
import org.springframework.stereotype.Service;

/**
 * @author hatak
 */
@Service
public class AuthUserDaoServiceImpl extends ServiceImpl<AuthUserMapper, AuthUser> implements IAuthUserDaoService {
    @Override
    public AuthUser findByName(String pName) {
        LambdaQueryWrapper<AuthUser> queryWrapper = Wrappers.<AuthUser>lambdaQuery()
                .eq(AuthUser::getName,pName);
        return baseMapper.selectOne(queryWrapper);
    }
}
