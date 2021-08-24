package indi.zhifa.learn.xdclass.busy.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinff qinfengfei@dimpt.com
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {

    /**
     * 是否启用swagger
     */
    private boolean enable = true;
    /**
     * swagger 组名
     */
    private String groupName = "业务接口";

    /**
     * 扫描包配置
     */
    private String apiPackage = "com.citc.**.api";

    /**
     * 路径正则
     */
    private String apiRegex = "/api/**";

    /**
     * 系统标题
     */
    private String title = "共建共享业务接口";
    /**
     * 系统描述
     */
    private String description = "共建共享的业务接口文档";
    /**
     * 系统版本
     */
    private String version = "1.0.0";
    /**
     * 联系人姓名
     */
    private String name = "";
    /**
     * 联系人邮箱
     */
    private String email = "";
    /**
     * 联系人地址
     */
    private String url = "";
}
