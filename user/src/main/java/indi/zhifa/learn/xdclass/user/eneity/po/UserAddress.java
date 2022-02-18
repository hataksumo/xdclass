package indi.zhifa.learn.xdclass.user.eneity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import indi.zhifa.learn.xdclass.common.entity.BasePo;
import indi.zhifa.learn.xdclass.user.eneity.po.json.UserAddressesData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "用户收件地址")
@Data
@TableName(autoResultMap = true, value = "auth_user")
public class UserAddress extends BasePo {
    @Schema(name = "用户Id")
    Long id;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    @Schema(name = "收件地址")
    UserAddressesData address;
}
