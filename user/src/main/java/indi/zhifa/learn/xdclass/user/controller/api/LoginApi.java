package indi.zhifa.learn.xdclass.user.controller.api;

import com.baomidou.kaptcha.Kaptcha;
import indi.zhifa.learn.xdclass.user.appconfig.AppConfig;
import indi.zhifa.learn.xdclass.user.eneity.annotation.UnLogin;
import indi.zhifa.learn.xdclass.user.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.user.eneity.vo.LoginResponse;
import indi.zhifa.learn.xdclass.user.service.IUserService;
import indi.zhifa.learn.xdclass.user.util.TokenUtil;
import indi.zhifa.learn.xdclass.common.entity.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Api(tags = "LoginApi:用户接口")
@RequestMapping("/api/v1.0/login")
@AllArgsConstructor
@RestController
public class LoginApi {

    final IUserService mUserService;
    final TokenUtil mTokenUtil;
    final Kaptcha mKaptcha;
    final AppConfig mAppConfig;

    @UnLogin
    @Operation(summary = "用户登录")
    @PostMapping("")
    RestResponse<LoginResponse> login(@ApiParam(value = "账号") @RequestParam(name = "userName") String pUserName,
                                      @ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd){
        LoginResponse loginResponse = new LoginResponse();
        AuthUser authUser = mUserService.checkUser(pUserName,pPasswd);
        if(null != authUser){
            loginResponse.setUser(authUser);
            TokenObject tokenObject = TokenObject.fromAuthUser(authUser);
            String token = mTokenUtil.createToken(tokenObject);
            loginResponse.setToken(token);
        }
        return RestResponse.ok(loginResponse);
    }

    @UnLogin
    @Operation(summary = "获取验证码")
    @GetMapping("/verify-code")
    void activeCode(){
        mKaptcha.render();
    }

    @UnLogin
    @Operation(summary = "用户登录")
    @PostMapping("/withCode")
    RestResponse<LoginResponse> loginWithVerify(
            @ApiParam(value = "账号") @RequestParam(name = "userName") String pUserName,
            @ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd,
            @ApiParam(value = "验证码") @RequestParam(name = "code") String pCode){

        mKaptcha.validate(pCode,mAppConfig.getSecurity().getVerifyCode().getExpire());

        LoginResponse loginResponse = new LoginResponse();
        AuthUser authUser = mUserService.checkUser(pUserName,pPasswd);
        if(null != authUser){
            loginResponse.setUser(authUser);
            TokenObject tokenObject = TokenObject.fromAuthUser(authUser);
            String token = mTokenUtil.createToken(tokenObject);
            loginResponse.setToken(token);
        }
        return RestResponse.ok(loginResponse);
    }
}
