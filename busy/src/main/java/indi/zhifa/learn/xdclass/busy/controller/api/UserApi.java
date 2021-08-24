package indi.zhifa.learn.xdclass.busy.controller.api;

import indi.zhifa.learn.xdclass.busy.eneity.annotation.UnLogin;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.busy.eneity.po.UserAddress;
import indi.zhifa.learn.xdclass.busy.eneity.po.UserProfile;
import indi.zhifa.learn.xdclass.busy.eneity.po.json.UserAddressItem;
import indi.zhifa.learn.xdclass.busy.service.IUserService;
import indi.zhifa.learn.xdclass.busy.util.TokenUtil;
import indi.zhifa.learn.xdclass.common.entity.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author hatak
 */
@Api(tags = "UserApi:用户接口")
@RequestMapping("/api/v1.0/user")
@AllArgsConstructor
@RestController
public class UserApi {

    final IUserService mUserService;
    final TokenUtil mTokenUtil;

    @UnLogin
    @Operation(summary = "用户登录")
    @PutMapping("")
    public RestResponse<AuthUser> createNew(@ApiParam(value = "用户信息") @RequestBody RegisterAuthUserDto pUserDto){
        AuthUser authUser = mUserService.createNew(pUserDto);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "修改用户-管理员")
    @PostMapping("/{id}")
    public RestResponse<AuthUser> editUser(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
                         @ApiParam(value = "用户信息") @RequestBody AuthUserDto pUserDto){
        AuthUser authUser = mUserService.editUser(pId,pUserDto);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "修改用户-自己")
    @PostMapping("")
    public RestResponse<AuthUser> editSelfUser(@ApiParam(value = "用户信息") @RequestBody AuthBaseUserInfo pUserDto){
        AuthUser authUser = mUserService.editSelfUser(pUserDto);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "更改密码")
    @PostMapping("/{id}/passwd")
    public RestResponse<AuthUser> changePasswd(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
                             @ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd){
        AuthUser authUser = mUserService.changePasswd(pId,pPasswd);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "更改密码-自己")
    @PostMapping("/self/passwd")
    public RestResponse<AuthUser> changeSelfPasswd(@ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd){
        TokenObject tokenObject = mTokenUtil.getTokenObject();
        Long selfId = tokenObject.getId();
        AuthUser authUser = mUserService.changePasswd(selfId,pPasswd);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "用户信息-删除")
    @DeleteMapping("/{id}")
    public RestResponse<String> delete(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        mUserService.delete(pId);
        return RestResponse.ok("删除成功");
    }

    @Operation(summary = "用户信息-获取")
    @GetMapping("/{id}")
    public RestResponse<AuthUser> info(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        AuthUser authUser = mUserService.info(pId);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "用户头像-获取")
    @GetMapping("/{id}/profile")
    public RestResponse<Void> getProfile(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        mUserService.getProfile(pId);
        return RestResponse.ok();
    }

    @Operation(summary = "用户头像-上传")
    @PostMapping("/{id}/profile")
    public RestResponse<UserProfile> uploadProfile(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
                                                   @ApiParam(value = "头像文件") @RequestPart("file") MultipartFile pFile) throws IOException {
        UserProfile userProfile = mUserService.uploadProfile(pId,pFile);
        return RestResponse.ok(userProfile);
    }

    @Operation(summary = "用户地址-获取")
    @GetMapping("/{id}/address")
    public RestResponse<UserAddress> getAddress(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        UserAddress userAddress = mUserService.getAddress(pId);
        return RestResponse.ok(userAddress);
    }

    @Operation(summary = "用户地址-编辑")
    @PostMapping("/{id}/address")
    public RestResponse<UserAddress> editAddress(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
                                                 @ApiParam(value = "地址列表", name= "userAddressList") @RequestBody List<UserAddressItem> pUserAddressList){
        UserAddress userAddress = mUserService.editAddress(pId,pUserAddressList);
        return RestResponse.ok(userAddress);
    }


}
