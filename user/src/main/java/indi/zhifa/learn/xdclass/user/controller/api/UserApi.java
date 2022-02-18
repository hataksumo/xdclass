package indi.zhifa.learn.xdclass.user.controller.api;


import indi.zhifa.learn.xdclass.common.entity.RestResponse;
import indi.zhifa.learn.xdclass.user.eneity.annotation.AccountAdmin;
import indi.zhifa.learn.xdclass.user.eneity.annotation.UnLogin;
import indi.zhifa.learn.xdclass.user.eneity.annotation.UserIdAuthCheck;
import indi.zhifa.learn.xdclass.user.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.user.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.user.eneity.dto.RegisterAuthUserDto;
import indi.zhifa.learn.xdclass.user.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.user.eneity.po.UserAddress;
import indi.zhifa.learn.xdclass.user.eneity.po.UserProfile;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressItem;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressesData;
import indi.zhifa.learn.xdclass.user.service.IUserService;
import indi.zhifa.learn.xdclass.user.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author hatak
 */
@Api(tags = "UserApi:用户接口")
@Validated
@RequestMapping("/api/v1.0/user")
@AllArgsConstructor
@RestController
public class UserApi {

    final IUserService mUserService;
    final TokenUtil mTokenUtil;

    @UnLogin
    //@AccountAdmin
    @Operation(summary = "创建用户")
    @PutMapping("")
    public RestResponse<AuthUser> createNew(
            @Validated @ApiParam(value = "用户信息") @RequestBody RegisterAuthUserDto pUserDto){
        AuthUser authUser = mUserService.createNew(pUserDto);
        return RestResponse.ok(authUser);
    }

    @UserIdAuthCheck
    @Operation(summary = "修改用户-管理员")
    @PostMapping("/{id}")
    public RestResponse<AuthUser> editUser(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @Validated @ApiParam(value = "用户信息") @RequestBody AuthUserDto pUserDto){
        AuthUser authUser = mUserService.editUser(pId,pUserDto);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "修改用户-自己")
    @PostMapping("/self")
    public RestResponse<AuthUser> editSelfUser(
            @Validated @ApiParam(value = "用户信息") @RequestBody AuthBaseUserInfo pUserDto){
        AuthUser authUser = mUserService.editSelfUser(pUserDto);
        return RestResponse.ok(authUser);
    }

    @UserIdAuthCheck
    @Operation(summary = "更改密码")
    @PostMapping("/{id}/passwd")
    public RestResponse<AuthUser> changePasswd(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd){
        AuthUser authUser = mUserService.changePasswd(pId,pPasswd);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "更改密码-自己")
    @PostMapping("/self/passwd")
    public RestResponse<AuthUser> changeSelfPasswd(
            @ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd){
        TokenObject tokenObject = mTokenUtil.getTokenObject();
        Long selfId = tokenObject.getId();
        AuthUser authUser = mUserService.changePasswd(selfId,pPasswd);
        return RestResponse.ok(authUser);
    }

    @AccountAdmin
    @Operation(summary = "用户信息-删除")
    @DeleteMapping("/{id}")
    public RestResponse<String> delete(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        mUserService.delete(pId);
        return RestResponse.ok("删除成功");
    }

    @UserIdAuthCheck
    @Operation(summary = "用户信息-获取")
    @GetMapping("/{id}")
    public RestResponse<AuthUser> info(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        AuthUser authUser = mUserService.info(pId);
        return RestResponse.ok(authUser);
    }

    @Operation(summary = "用户头像-获取")
    @GetMapping("/{id}/profile")
    public RestResponse<Void> getProfile(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        mUserService.getProfile(pId);
        return RestResponse.ok();
    }

    @UserIdAuthCheck
    @Operation(summary = "用户头像-上传")
    @PostMapping("/{id}/profile")
    public RestResponse<UserProfile> uploadProfile(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @ApiParam(value = "头像文件") @RequestPart("file") MultipartFile pFile) throws IOException {
        UserProfile userProfile = mUserService.uploadProfile(pId,pFile);
        return RestResponse.ok(userProfile);
    }

    @UserIdAuthCheck
    @Operation(summary = "用户地址-获取")
    @GetMapping("/{id}/address")
    public RestResponse<UserAddress> getAddress(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){
        UserAddress userAddress = mUserService.getAddress(pId);
        return RestResponse.ok(userAddress);
    }

    @UserIdAuthCheck
    @Operation(summary = "用户地址-编辑")
    @PostMapping("/{id}/address")
    public RestResponse<UserAddress> editAddress(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @ApiParam(value = "地址列表", name= "userAddressList") @RequestBody UserAddressesData pUserAddress){
        UserAddress userAddress = mUserService.editAddress(pId,pUserAddress);
        return RestResponse.ok(userAddress);
    }

    /*用户收货地址编辑的辅助接口*/

    @UserIdAuthCheck
    @Operation(summary = "用户地址-添加")
    @PostMapping("/{id}/address/add")
    public RestResponse<UserAddress> AddAddress(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @ApiParam(value = "是否默认") @RequestParam(name = "isDefault") Boolean pSetDefault,
            @ApiParam(value = "地址", name= "userAddressItem") @RequestBody UserAddressItem pUserAddressItem){
        UserAddress userAddress = mUserService.addAddress(pId,pUserAddressItem,pSetDefault);
        return RestResponse.ok(userAddress);
    }

    @UserIdAuthCheck
    @Operation(summary = "用户地址-删除")
    @DeleteMapping("/{id}/address/delete")
    public RestResponse<UserAddress> AddAddress(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @ApiParam(value = "第几个地址") @RequestParam(name = "idx") Integer pIdx,
            @Range(min = 0, message = "索引必须大于0") @ApiParam(value = "默认地址") @RequestParam(name = "isDefault", required = false) Integer pDefaultIdx){
        UserAddress userAddress = mUserService.delAddress(pId,pIdx,pDefaultIdx);
        return RestResponse.ok(userAddress);
    }

    @UserIdAuthCheck
    @Operation(summary = "用户地址-设置默认")
    @PostMapping("/{id}/address/default")
    public RestResponse<UserAddress> DefaultAddress(
            @ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
            @Range(min = 0, message = "索引必须大于0") @ApiParam(value = "默认地址") @RequestParam(name = "isDefault", required = false) Integer pDefaultIdx){
        UserAddress userAddress = mUserService.setDefaultAddress(pId,pDefaultIdx);
        return RestResponse.ok(userAddress);
    }


}
