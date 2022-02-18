package indi.zhifa.learn.xdclass.user.controller.filter;

import indi.zhifa.learn.xdclass.user.eneity.annotation.UnLogin;
import indi.zhifa.learn.xdclass.user.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.user.util.TokenUtil;
import indi.zhifa.learn.xdclass.user.util.WordsUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
@Slf4j
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    final TokenUtil mTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthException {
        if(hasUnLogin(handler)){
            return true;
        }

        String token = request.getHeader(WordsUtil.tokenKey);
        if(!StringUtils.hasLength(token)){
            outputError(response,"token 为空");
            return false;
        }
        try{
            TokenObject tokenObject = mTokenUtil.parseToken(token);
            mTokenUtil.memoToken(tokenObject);
        }catch (Exception ex){
            outputError(response,ex.toString());
            return false;
        }
        return true;
    }

    protected boolean hasUnLogin(Object pHandler){
        if(pHandler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) pHandler;
            // 获取方法上的注解
            UnLogin requiredPermission = handlerMethod.getMethod().getAnnotation(UnLogin.class);
            if(null != requiredPermission){
                return true;
            }else{
                return false;
            }
        }
        return false;
    }

    protected void outputError(HttpServletResponse response, String pErrMsg){
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            writer = response.getWriter();
            writer.print(pErrMsg);
        }catch (Exception ex){
            log.debug("输出错误时发生故障，故障信息是 "+ex.toString());
        }

    }


}
