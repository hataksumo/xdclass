package indi.zhifa.learn.xdclass.busy.controller.api;

import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthBaseUserInfo;
import indi.zhifa.learn.xdclass.busy.eneity.dto.AuthUserDto;
import indi.zhifa.learn.xdclass.busy.eneity.dto.RegisterAuthUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author hatak
 */
@Api(value = "UserApi:用户接口")
@RequestMapping("/api/v1.0/user")
@AllArgsConstructor
@RestController
public class UserApi {

    @Operation(summary = "注册用户")
    @PutMapping("")
    public void createNew(@ApiParam(value = "用户信息") @RequestBody RegisterAuthUserDto pUserDto){

    }

    @Operation(summary = "修改用户-管理员")
    @PostMapping("/{id}")
    public void editUser(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
                         @ApiParam(value = "用户信息") @RequestBody AuthUserDto pUserDto){

    }

    @Operation(summary = "修改用户-自己")
    @PostMapping("")
    public void editSelfUser(@ApiParam(value = "用户信息") @RequestBody AuthBaseUserInfo pUserDto){

    }

    @PostMapping("/{id}/passwd")
    public void changePasswd(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId,
                             @ApiParam(value = "密码") @RequestParam(name = "passwd") String pPasswd){

    }

    @DeleteMapping("/{id}")
    public void delete(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){

    }

    @GetMapping("/{id}")
    public void info(@ApiParam(value = "用户Id") @PathVariable(name = "id") Long pId){

    }
}
