package indi.zhifa.learn.xdclass.busy.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.http.HttpUtil;
import indi.zhifa.learn.xdclass.busy.dao.service.IAuthUserDaoService;
import indi.zhifa.learn.xdclass.busy.dao.service.IUserAddressDoaService;
import indi.zhifa.learn.xdclass.busy.dao.service.IUserProfileDaoService;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.busy.eneity.po.UserAddress;
import indi.zhifa.learn.xdclass.busy.eneity.po.UserProfile;
import indi.zhifa.learn.xdclass.busy.eneity.po.json.UserAddressItem;
import indi.zhifa.learn.xdclass.busy.service.IUserService;
import indi.zhifa.learn.xdclass.busy.util.DownloadUtil;
import indi.zhifa.learn.xdclass.busy.util.IMinioUtil;
import indi.zhifa.learn.xdclass.busy.util.ImageUtil;
import indi.zhifa.learn.xdclass.busy.util.TokenUtil;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import io.minio.GetObjectResponse;
import io.netty.util.NetUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
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
        List<String> roleList = Arrays.asList(ERole.CUSTOMER.getName());
        authUser.setRoles(roleList);
        mAuthUserDaoService.save(authUser);
        return authUser;
    }

    @Override
    public AuthUser editUser(Long pId, AuthUserDto pUserDto) {
        AuthUser authUser = mAuthUserDaoService.getById(pId);
        if(null == authUser){
            throw new ServiceException("没有找到id为["+pId+"]的用户");
        }
        AuthUser newUser = DtoEntityUtil.dtoToPo(pUserDto,authUser,AuthUser.class);
        mAuthUserDaoService.save(newUser);
        return newUser;
    }

    @Override
    public AuthUser editSelfUser(AuthBaseUserInfo pUserDto) {
        TokenObject tokenObject = tokenUtil.getTokenObject();
        AuthUser authUser = checkUser(tokenObject.getId());
        AuthUser newUser = DtoEntityUtil.dtoToPo(pUserDto,authUser,AuthUser.class);
        mAuthUserDaoService.save(newUser);
        return newUser;
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

    @Override
    public UserAddress getAddress(Long pId) {
        UserAddress userAddress = mUserAddressDoaService.getById(pId);
        if(null == userAddress){
            throw new ServiceException("没有找到Id为"+pId+"的用户");
        }
        return userAddress;
    }

    @Override
    public UserAddress editAddress(Long pId, List<UserAddressItem> pAddress) {
        UserAddress userAddress = getAddress(pId);
        userAddress.updateInit();
        userAddress.setAddress(pAddress.toArray(new UserAddressItem[0]));
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
