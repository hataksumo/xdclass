package indi.zhifa.learn.xdclass.user.controller.filter;

import indi.zhifa.learn.xdclass.common.entity.RestResponse;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import indi.zhifa.learn.xdclass.user.eneity.annotation.AccountAdmin;
import indi.zhifa.learn.xdclass.user.eneity.annotation.UserIdAuthCheck;
import indi.zhifa.learn.xdclass.user.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.user.eneity.enums.ERole;
import indi.zhifa.learn.xdclass.user.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
public class UserRoleFilters {

    final TokenUtil mTokenUtil;

    @Around("execution(* indi.zhifa.learn.xdclass.user.controller.api..*(..)) && @annotation(permission)")
    public Object spAdminAop(ProceedingJoinPoint joinPoint, AccountAdmin permission) throws Throwable {
        TokenObject tokenObject = mTokenUtil.getTokenObject();
        ERole[] roles = tokenObject.getRoles();
        boolean hasAdm = false;
        for(ERole role : roles){
            if(role == ERole.ACCOUNT_ADM){
                hasAdm = true;
                break;
            }
        }
        if(!hasAdm){
            return RestResponse.unAuthorise(400,"您没有操作该接口的权限，需要ACCOUNT_ADM");
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

    @Around("execution(* indi.zhifa.learn.xdclass.user.controller.api..*(..)) && @annotation(permission)")
    public Object userIdAuthCheckApo(ProceedingJoinPoint joinPoint, UserIdAuthCheck permission) throws Throwable {
        TokenObject tokenObject = mTokenUtil.getTokenObject();
        ERole[] roles = tokenObject.getRoles();
        boolean hasAdm = false;
        for(ERole role : roles){
            if(role == ERole.ACCOUNT_ADM){
                hasAdm = true;
                break;
            }
        }
        if(!hasAdm){
            Long requestUserId = getParamUserId(joinPoint,permission.idParam());
            if(requestUserId.equals(tokenObject.getId())){
                return joinPoint.proceed(joinPoint.getArgs());
            }else{
                return RestResponse.unAuthorise(401,permission.errMsg());
            }
        }
        return joinPoint.proceed(joinPoint.getArgs());
    }

    protected Long getParamUserId(JoinPoint pJoinPoint, String pParamName){
        Signature signature = pJoinPoint.getSignature();
        String[] parameterNames = ((MethodSignature) signature).getParameterNames();
        Object[] params = pJoinPoint.getArgs();
        Object oId = getParam(parameterNames,params,pParamName);
        if(null == oId){
            throw new ServiceException("BaseIotAop 拦截器中，没有找到名字为"+pParamName+" 的参数");
        }
        return (Long)oId;
    }

    protected Object getParam(String[] pParameterNames, Object[] pParams ,String pParamName){
        for(int i=0;i<pParams.length;i++){
            if(pParameterNames[i].equals(pParamName)){
                return pParams[i];
            }
        }
        return null;
    }

}
