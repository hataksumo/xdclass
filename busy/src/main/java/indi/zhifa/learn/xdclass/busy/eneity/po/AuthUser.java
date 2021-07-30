package indi.zhifa.learn.xdclass.busy.eneity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import indi.zhifa.learn.xdclass.common.entity.BasePo;
import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import lombok.Data;

import java.util.List;

/**
 * @author hatak
 */
@Data
@TableName(autoResultMap = true)
public class AuthUser extends BasePo {
    String name;
    String passwd;
    String nickName;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    List<ERole> roleName;
}
