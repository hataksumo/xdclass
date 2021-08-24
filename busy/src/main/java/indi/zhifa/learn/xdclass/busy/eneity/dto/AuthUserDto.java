package indi.zhifa.learn.xdclass.busy.eneity.dto;

import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("用户实体")
public class AuthUserDto {
    @ApiModelProperty("用户名")
    String name;
    @ApiModelProperty("昵称")
    String nickName;
    @Schema(name = "邮箱")
    String email;
    @Schema(name = "电话")
    String phone;
    @ApiModelProperty("角色列表")
    List<String> roles;
}
