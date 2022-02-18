package indi.zhifa.learn.xdclass.common.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

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
                .apis(basePackage(mProperties.getApiPackage()))
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

    public static Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).map(handlerPackage(basePackage)).orElse(true);
    }

    private static Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(";")) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private static Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.ofNullable(input.declaringClass());
    }
}
