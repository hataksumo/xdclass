package indi.zhifa.learn.xdclass.user.eneity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Schema(name = "用户实体")
@Data
public class AuthBaseUserInfo {
    @Schema(name = "昵称")
    String nickName;
    @Schema(name = "邮箱")
    @Email(message = "邮箱不合法")
    @NotNull(message = "邮箱不可为空")
    String email;
    @NotNull(message = "电话不可为空")
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",message = "手机号不合法")
    @Schema(name = "电话")
    String phone;
}
