package indi.zhifa.learn.xdclass.busy.eneity.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import lombok.Data;

import java.util.List;

/**
 * @author hatak
 */
@Data
public class AuthUserDto {
    String name;
    String passwd;
    String nickName;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    List<ERole> roleName;
}
