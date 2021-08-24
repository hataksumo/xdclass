package indi.zhifa.learn.xdclass.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import indi.zhifa.learn.xdclass.common.util.SnowflakeIdWorker;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author hatak
 */
@Data
public class BasePo {
    @Schema(name = "主键")
    @TableId(type = IdType.INPUT)
    Long id;
    @Schema(name = "创建时间")
    LocalDateTime gmtCreate;
    @Schema(name = "修改时间")
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
