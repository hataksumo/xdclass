package indi.zhifa.learn.xdclass.user.eneity.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hatak
 */

@AllArgsConstructor
public enum ERole {
    /**
     * 可以对账号进行管理
     */
    ACCOUNT_ADM(1,"ACCOUNT_ADM"),
    /**
     * 系统管理员，改变系统设置
     */
    SYS_ADM(2,"SYS_ADM"),
    /**
     * 商家
     */
    ENTERPRISE(3,"ENTERPRISE"),
    /**
     * 用户
     */
    CUSTOMER(4,"CUSTOMER");

    @Getter
    @EnumValue
    int code;
    @Getter
    String name;
}
