package indi.zhifa.learn.xdclass.common.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import indi.zhifa.learn.xdclass.common.util.SnowflakeIdWorker;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hatak
 */
@Data
public class BasePo {
    @TableId
    Long id;
    LocalDateTime gmtCreate;
    LocalDateTime gmtUpdate;

    public void createInit(){
        id = SnowflakeIdWorker.generateId();
        gmtCreate = LocalDateTime.now();
        gmtUpdate = LocalDateTime.now();
    }

    public void updateInit(){
        gmtUpdate = LocalDateTime.now();
    }
}
