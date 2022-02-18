package indi.zhifa.learn.xdclass.user.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import indi.zhifa.learn.xdclass.user.appconfig.AppConfig;
import indi.zhifa.learn.xdclass.user.appconfig.security.SecurityWordConfig;
import indi.zhifa.learn.xdclass.user.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class TokenUtil {

    private final AppConfig mAppConfig;
    private Algorithm algorithm;
    private JWTVerifier verifier;
    private SecurityWordConfig mSecurityConfig;

    public TokenUtil(AppConfig pAppConfig){
        mAppConfig = pAppConfig;
    }

    @PostConstruct
    public void init(){
        mSecurityConfig = mAppConfig.getSecurity();
        algorithm = Algorithm.HMAC256(mSecurityConfig.getToken().getSecret());
        verifier = JWT.require(algorithm).build();
    }

    public String createToken(TokenObject pTokenObject){
        pTokenObject.setExpire(LocalDateTime.now().plusSeconds(mSecurityConfig.getToken().getDuration()));
        String token = JWT.create()
                .withSubject(JSON.toJSONString(pTokenObject))
                .sign(algorithm);
        return token;
    }

    public TokenObject memoToken(TokenObject pTokenObject){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("token",pTokenObject);
        return pTokenObject;
    }

    public TokenObject getTokenObject(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Object objTokenObject = request.getAttribute("token");
        if(null == objTokenObject){
            throw new ServiceException("还没有设置token");
        }
        if(!(objTokenObject instanceof TokenObject)){
            throw new ServiceException("无法转换为TokenObject");
        }
        return (TokenObject) objTokenObject;
    }

    public TokenObject parseToken(String pToken){
        try{
            DecodedJWT decodedJWT = JWT.decode(pToken);
            try{
                decodedJWT = verifier.verify(decodedJWT);
            }catch (JWTVerificationException jwtVerificationException){
                throw new ServiceException("签名不合法");
            }
            String subject = decodedJWT.getSubject();
            TokenObject tokenObject = JSON.parseObject(subject,TokenObject.class);
            LocalDateTime expire = tokenObject.getExpire();
            if(LocalDateTime.now().isAfter(expire)){
                throw new AuthException("token过期");
            }

            return tokenObject;
        }
        catch (Exception ex){
            throw new ServiceException("解析token时发生错误，错误信息是: "+ex.toString());
        }

    }
}
