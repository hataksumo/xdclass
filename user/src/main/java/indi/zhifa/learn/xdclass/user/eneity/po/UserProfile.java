package indi.zhifa.learn.xdclass.user.eneity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import indi.zhifa.learn.xdclass.common.entity.BasePo;
import lombok.Data;

@Data
@TableName(autoResultMap = true, value = "auth_user")
public class UserProfile extends BasePo {
    Long id;
    String profilePath;
}
