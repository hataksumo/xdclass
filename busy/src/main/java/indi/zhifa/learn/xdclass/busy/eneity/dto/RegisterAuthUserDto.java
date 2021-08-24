package indi.zhifa.learn.xdclass.busy.eneity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author hatak
 */
@Data
@Schema(name = "注册用户实体")
public class RegisterAuthUserDto {
    @Schema(name = "用户名")
    String name;
    @Schema(name = "密码")
    String passwd;
    @Schema(name = "昵称")
    String nickName;
    @Schema(name = "邮箱")
    String email;
    @Schema(name = "电话")
    String phone;
}
