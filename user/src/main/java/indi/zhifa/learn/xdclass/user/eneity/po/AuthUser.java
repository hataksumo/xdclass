package indi.zhifa.learn.xdclass.user.eneity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import indi.zhifa.learn.xdclass.common.entity.BasePo;
import indi.zhifa.learn.xdclass.user.eneity.enums.ERole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author hatak
 */
@Schema(name = "用户实体")
@Data
@TableName(autoResultMap = true, value = "auth_user")
public class AuthUser extends BasePo {
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
    @Schema(name = "角色")
    @TableField(typeHandler = FastjsonTypeHandler.class)
    ERole[] roles;
}
