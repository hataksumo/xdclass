package indi.zhifa.learn.xdclass.user.eneity.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserIdAuthCheck {
    String idParam() default "pId";
    String errMsg() default "您没有访问该用户的权限";
}
