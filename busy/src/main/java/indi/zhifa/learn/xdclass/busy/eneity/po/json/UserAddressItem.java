package indi.zhifa.learn.xdclass.busy.eneity.po.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "用户地址")
@Data
public class UserAddressItem {
    @Schema(name = "省份")
    String province;
    @Schema(name = "城市")
    String city;
    @Schema(name = "邮编")
    int addressCode;
    @Schema(name = "邮编详情")
    String addressDetail;
    @Schema(name = "接收人昵称")
    String name;
    @Schema(name = "接收人电话")
    String tel;
}
