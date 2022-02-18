package indi.zhifa.learn.xdclass.user.eneity.dto;

import indi.zhifa.learn.xdclass.user.eneity.enums.ERole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@ApiModel("用户实体")
public class AuthUserDto {
    @NotNull(message = "账号名不可为空")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_-]{3,16}$",message = "用户名必须是4到16位（字母，数字，_，-）,并且以字母开头")
    @ApiModelProperty("用户名")
    String name;
    @ApiModelProperty("昵称")
    String nickName;
    @NotNull(message = "邮箱不可为空")
    @Email(message = "邮箱不合法")
    @Schema(name = "邮箱")
    String email;
    @NotNull(message = "电话不可为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",message = "手机号不合法")
    @Schema(name = "电话")
    String phone;
    @ApiModelProperty("角色列表")
    ERole[] roles;
}
