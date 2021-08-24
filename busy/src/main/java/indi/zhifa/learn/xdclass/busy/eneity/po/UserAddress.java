package indi.zhifa.learn.xdclass.busy.eneity.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import indi.zhifa.learn.xdclass.busy.eneity.po.json.UserAddressItem;
import indi.zhifa.learn.xdclass.common.entity.BasePo;
import indi.zhifa.learn.xdclass.common.util.FastjsonArrayTypeHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(name = "用户收件地址")
@Data
@TableName(autoResultMap = true, value = "auth_user")
public class UserAddress extends BasePo {
    @Schema(name = "用户Id")
    Long id;
    @TableField(typeHandler = FastjsonTypeHandler.class)
    @Schema(name = "收件地址")
    UserAddressItem[] address;
}
