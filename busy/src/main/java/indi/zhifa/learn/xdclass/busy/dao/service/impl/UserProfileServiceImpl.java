package indi.zhifa.learn.xdclass.busy.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zhifa.learn.xdclass.busy.dao.mapper.UserProfileMapper;
import indi.zhifa.learn.xdclass.busy.dao.service.IUserProfileDaoService;
import indi.zhifa.learn.xdclass.busy.eneity.po.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileMapper, UserProfile> implements IUserProfileDaoService {
}
