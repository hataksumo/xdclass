package indi.zhifa.learn.xdclass.busy.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
/**
 * @author 芝法酱
 */
@Configuration
@EnableOpenApi

public class Knife4jConfiguration {

    public static final String KEY_AUTHORIZATION = "Authorization";
    private final SwaggerProperties mProperties;

    public Knife4jConfiguration(SwaggerProperties pProperties){
        mProperties = pProperties;
    }

    @Bean
    public Docket knife4jDocket() {
        Docket docket=new Docket(DocumentationType.OAS_30)
                .groupName(mProperties.getGroupName())
                .pathMapping("/")
                .enable(mProperties.isEnable())
                .apiInfo(getApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(mProperties.getApiPackage()))
                .paths(PathSelectors.ant(mProperties.getApiRegex()))
                .build();

        return docket;
    }

    private ApiInfo getApiInfo(){
        return new ApiInfoBuilder()
                .title(mProperties.getTitle())
                .description(mProperties.getDescription())
                .contact(new Contact(mProperties.getName(),mProperties.getUrl(),mProperties.getEmail()))
                .version(mProperties.getVersion())
                .build();
    }
}
