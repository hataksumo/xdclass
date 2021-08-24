package indi.zhifa.learn.xdclass.busy.eneity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "用户实体")
public class AuthBaseUserInfo {
    @Schema(name = "昵称")
    String nickName;
    @Schema(name = "邮箱")
    String email;
    @Schema(name = "电话")
    String phone;
}
