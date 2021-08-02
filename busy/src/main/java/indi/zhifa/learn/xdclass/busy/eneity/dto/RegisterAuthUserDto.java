package indi.zhifa.learn.xdclass.busy.eneity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author hatak
 */
@Data
@ApiModel("注册用户实体")
public class RegisterAuthUserDto {
    @ApiModelProperty("用户名")
    String name;
    @ApiModelProperty("密码")
    String passwd;
    @ApiModelProperty("昵称")
    String nickName;
}
