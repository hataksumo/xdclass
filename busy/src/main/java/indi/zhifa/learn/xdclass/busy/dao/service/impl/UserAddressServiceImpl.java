package indi.zhifa.learn.xdclass.busy.dao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import indi.zhifa.learn.xdclass.busy.dao.mapper.UserAddressMapper;
import indi.zhifa.learn.xdclass.busy.dao.service.IUserAddressDoaService;
import indi.zhifa.learn.xdclass.busy.eneity.po.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserAddressServiceImpl extends ServiceImpl<UserAddressMapper, UserAddress> implements IUserAddressDoaService {
}
