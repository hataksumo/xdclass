package indi.zhifa.learn.xdclass.user.service.impl;

import cn.hutool.core.io.FileUtil;
import indi.zhifa.learn.xdclass.user.appconfig.AppConfig;
import indi.zhifa.learn.xdclass.user.dao.service.IAuthUserDaoService;
import indi.zhifa.learn.xdclass.user.dao.service.IUserAddressDoaService;
import indi.zhifa.learn.xdclass.user.dao.service.IUserProfileDaoService;
import indi.zhifa.learn.xdclass.user.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.user.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.user.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.user.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.user.eneity.enums.ERole;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.user.eneity.po.UserAddress;
import indi.zhifa.learn.xdclass.user.eneity.po.UserProfile;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressItem;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressesData;
import indi.zhifa.learn.xdclass.user.service.IUserService;
import indi.zhifa.learn.xdclass.user.util.DownloadUtil;
import indi.zhifa.learn.xdclass.user.util.IMinioUtil;
import indi.zhifa.learn.xdclass.user.util.ImageUtil;
import indi.zhifa.learn.xdclass.user.util.TokenUtil;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import io.minio.GetObjectResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author hatak
 */
@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    final PasswordEncoder passwordEncoder;
    final IAuthUserDaoService mAuthUserDaoService;
    final IUserAddressDoaService mUserAddressDoaService;
    final IUserProfileDaoService mUserProfileDaoService;
    final TokenUtil tokenUtil;
    final IMinioUtil mMinioUtil;
    final AppConfig mAppConfig;

    @Override
    public AuthUser checkUser(String pUserName, String pPasswd) {
        AuthUser authUser = mAuthUserDaoService.findByName(pUserName);
        if(null == authUser){
            throw new ServiceException("没有找到用户名为["+pUserName+"]的用户");
        }
        if(passwordEncoder.matches(pPasswd,authUser.getPasswd())){
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
        authUser.setRoles(new ERole[0]);
        try{
            mAuthUserDaoService.save(authUser);
        }catch (DataIntegrityViolationException ex){
            // 在高并发下，可能会2个请求同时进入唯一索引判定，而后各自存储
            throw new ServiceException("存储数据时发生错误，可能是数据库中已存在该用户。或其他唯一主键冲突。");
        }

        return authUser;
    }

    @Override
    public AuthUser editUser(Long pId, AuthUserDto pUserDto) {
        AuthUser authUser = mAuthUserDaoService.getById(pId);
        if(null == authUser){
            throw new ServiceException("没有找到id为["+pId+"]的用户");
        }
        AuthUser newUser = DtoEntityUtil.dtoToPo(pUserDto,authUser,AuthUser.class);
        mAuthUserDaoService.updateById(newUser);
        return newUser;
    }

    @Override
    public AuthUser editSelfUser(AuthBaseUserInfo pUserDto) {
        TokenObject tokenObject = tokenUtil.getTokenObject();
        AuthUser authUser = checkUser(tokenObject.getId());
        AuthUser newUser = DtoEntityUtil.dtoToPo(pUserDto,authUser,AuthUser.class);
        mAuthUserDaoService.updateById(newUser);
        return newUser;
    }

    @Override
    public AuthUser changePasswd(Long pId, String pPasswd) {
        AuthUser authUser = checkUser(pId);
        String passwd = passwordEncoder.encode(pPasswd);
        authUser.setPasswd(passwd);
        mAuthUserDaoService.updateById(authUser);
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

    @Override
    public UserAddress getAddress(Long pId) {
        UserAddress userAddress = mUserAddressDoaService.getById(pId);
        if(null == userAddress){
            throw new ServiceException("没有找到Id为"+pId+"的用户");
        }
        return userAddress;
    }

    @Override
    public UserAddress editAddress(Long pId, UserAddressesData pUserAddress) {
        UserAddress userAddress = getAddress(pId);
        userAddress.updateInit();
        userAddress.setAddress(pUserAddress);
        mUserAddressDoaService.updateById(userAddress);
        return userAddress;
    }

    @Override
    public UserAddress addAddress(Long pId, UserAddressItem pUserAddressItem, Boolean pSetDefault) {
        UserAddress userAddress = getAddress(pId);
        UserAddressesData userAddressesData = userAddress.getAddress();
        UserAddressItem[] userAddressItems =  userAddressesData.getAddress();
        List<UserAddressItem> userAddressItemList = Arrays.asList(userAddressItems);
        if(userAddressItemList.size()>= mAppConfig.getUser().getMaxAddress()){
            throw new ServiceException("最大地址数为 "+mAppConfig.getUser().getMaxAddress()+", 用户已保存地址数达到上限");
        }
        userAddressItemList.add(pUserAddressItem);
        if(pSetDefault){
            userAddressesData.setDefaultIdx(userAddressItemList.size()-1);
        }
        userAddress.updateInit();
        mUserAddressDoaService.updateById(userAddress);
        return userAddress;
    }

    @Override
    public UserAddress delAddress(Long pId, Integer pIndex, Integer pDefaultIdx) {
        UserAddress userAddress = getAddress(pId);
        UserAddressesData userAddressesData = userAddress.getAddress();
        UserAddressItem[] userAddressItems =  userAddressesData.getAddress();
        if(pIndex >= userAddressItems.length){
            throw new ServiceException("要删除的索引大于已存在的地址数量");
        }
        UserAddressItem[] newUserAddressItems = new UserAddressItem[userAddressItems.length-1];
        int idx = 0;
        for(int i=0;i<userAddressItems.length;i++){
            if(i!=pIndex){
                newUserAddressItems[idx++] = userAddressItems[i];
            }
        }
        userAddressesData.setAddress(newUserAddressItems);
        if(null != pDefaultIdx){
            if(pDefaultIdx > newUserAddressItems.length){
                throw new ServiceException("默认地址编号大于地址数量");
            }
            userAddressesData.setDefaultIdx(pDefaultIdx);
        }
        userAddress.updateInit();
        mUserAddressDoaService.updateById(userAddress);
        return userAddress;
    }

    @Override
    public UserAddress setDefaultAddress(Long pId, Integer pDefaultIdx) {
        UserAddress userAddress = getAddress(pId);
        UserAddressesData userAddressesData = userAddress.getAddress();
        UserAddressItem[] userAddressItems =  userAddressesData.getAddress();
        if(pDefaultIdx >= userAddressItems.length){
            throw new ServiceException("要删除的索引大于已存在的地址数量");
        }
        userAddressesData.setDefaultIdx(pDefaultIdx);
        userAddress.updateInit();
        mUserAddressDoaService.updateById(userAddress);
        return userAddress;
    }

    protected UserProfile checkUserProfile(Long pId){
        UserProfile userProfile = mUserProfileDaoService.getById(pId);
        if(null == userProfile){
            throw new ServiceException("没有找到Id为"+pId+"的用户");
        }
        return userProfile;
    }

    @Override
    public void getProfile(Long pId) {
        UserProfile userProfile = checkUserProfile(pId);
        String filePath = userProfile.getProfilePath();
        if(!StringUtils.hasLength(filePath)){
            throw new ServiceException("用户"+pId+"还没有上传头像");
        }
        GetObjectResponse minioResponse = mMinioUtil.getFile(filePath);
        int lastIndex = filePath.lastIndexOf("_");
        String fileName = filePath.substring(lastIndex);
        DownloadUtil.download(fileName,minioResponse);
    }

    @Override
    public UserProfile uploadProfile(Long pId, MultipartFile pMultipartFile) throws IOException {
        UserProfile userProfile = checkUserProfile(pId);
        AuthUser authUser = checkUser(pId);
        if(StringUtils.hasLength(userProfile.getProfilePath())){
            mMinioUtil.deleteFile(userProfile.getProfilePath());
        }

        if(pMultipartFile.getSize() <=0){
            throw new ServiceException("上传你的头像为空");
        }

        byte[] bufferedImage = ImageUtil.resizeImage(pMultipartFile.getInputStream(),600,600);
        String name = pMultipartFile.getOriginalFilename();
        String fileName = FileUtil.mainName(name);
        String prefix = FileUtil.extName(name);
        InputStream bufferedImageIpt = new ByteArrayInputStream(bufferedImage);

        List<String> folder = new ArrayList<>();
        folder.add("profiles");
        folder.add(authUser.getName());
        String path = mMinioUtil.putFile(folder,fileName,prefix,bufferedImageIpt,bufferedImage.length);
        userProfile.setProfilePath(path);
        mUserProfileDaoService.updateById(userProfile);
        return userProfile;
    }

    protected AuthUser checkUser(Long pId){
        AuthUser authUser = mAuthUserDaoService.getById(pId);
        if(null == authUser){
            throw new ServiceException("没有找到id为["+pId+"]的用户");
        }
        return authUser;
    }
}
