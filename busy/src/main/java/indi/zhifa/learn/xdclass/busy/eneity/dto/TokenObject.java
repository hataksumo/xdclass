package indi.zhifa.learn.xdclass.busy.eneity.dto;

import indi.zhifa.learn.xdclass.busy.eneity.enums.ERole;
import lombok.Data;

import java.util.List;

@Data
public class TokenObject {
    Long id;
    String userName;
    List<ERole> roleName;
}
