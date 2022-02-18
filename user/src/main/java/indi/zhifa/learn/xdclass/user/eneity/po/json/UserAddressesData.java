package indi.zhifa.learn.xdclass.user.eneity.po.json;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "收件地址")
@Data
public class UserAddressesData {
    int defaultIdx;
    @Schema(name = "收件地址列表")
    UserAddressItem[] address;
}
