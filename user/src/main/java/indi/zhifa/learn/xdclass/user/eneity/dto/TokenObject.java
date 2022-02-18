package indi.zhifa.learn.xdclass.user.eneity.dto;

import indi.zhifa.learn.xdclass.user.eneity.enums.ERole;
import indi.zhifa.learn.xdclass.user.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TokenObject {
    Long id;
    String userName;
    ERole[] roles;
    LocalDateTime expire;

    static public TokenObject fromAuthUser(AuthUser pUser){
        TokenObject tokenObject = DtoEntityUtil.trans(pUser,TokenObject.class);
        return tokenObject;
    }
}
