package indi.zhifa.learn.xdclass.busy.eneity.dto;

import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import indi.zhifa.learn.xdclass.busy.eneity.po.AuthUser;
import indi.zhifa.learn.xdclass.common.util.DtoEntityUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TokenObject {
    Long id;
    String userName;
    List<String> roles;
    LocalDateTime expire;

    static public TokenObject fromAuthUser(AuthUser pUser){
        TokenObject tokenObject = DtoEntityUtil.trans(pUser,TokenObject.class);
        return tokenObject;
    }
}
