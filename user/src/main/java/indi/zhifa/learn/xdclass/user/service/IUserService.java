package indi.zhifa.learn.xdclass.user.service;

import indi.zhifa.learn.xdclass.user.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.user.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.user.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.user.eneity.po.UserAddress;
import indi.zhifa.learn.xdclass.user.eneity.po.UserProfile;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressItem;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressesData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hatak
 */
public interface IUserService {
    AuthUser checkUser(String pUserName, String pPasswd);
    AuthUser createNew(RegisterAuthUserDto pUserDto);
    AuthUser editUser(Long pId, AuthUserDto pUserDto);
    AuthUser editSelfUser(AuthBaseUserInfo pUserDto);
    AuthUser changePasswd(Long pId,String pPasswd);
    boolean delete(Long pId);
    AuthUser info(Long pId);
    UserAddress getAddress(Long pId);
    UserAddress editAddress(Long pId, UserAddressesData pUserAddress);
    UserAddress addAddress(Long pId, UserAddressItem pUserAddressItem, Boolean pSetDefault);
    UserAddress delAddress(Long pId, Integer pIndex, Integer pDefaultIdx);
    UserAddress setDefaultAddress(Long pId, Integer pDefaultIdx);


    void getProfile(Long pId);
    UserProfile uploadProfile(Long pId, MultipartFile pMultipartFile) throws IOException;
}
