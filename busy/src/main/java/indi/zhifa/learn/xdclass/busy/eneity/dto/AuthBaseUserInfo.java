package indi.zhifa.learn.xdclass.busy.eneity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("用户实体")
public class AuthBaseUserInfo {
    @ApiModelProperty("昵称")
    String nickName;
}
