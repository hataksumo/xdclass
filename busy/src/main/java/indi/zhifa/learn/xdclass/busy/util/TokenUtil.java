package indi.zhifa.learn.xdclass.busy.util;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import indi.zhifa.learn.xdclass.busy.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.common.entity.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
public class TokenUtil {

    @Value("${security.token.serc}")
    String serc;
    @Value("${security.token.duration}")
    Long duration;

    public String createToken(TokenObject pTokenObject){
        pTokenObject.setExpire(LocalDateTime.now().minusSeconds(duration));
        String token = JWT.create()
                .withSubject(JSON.toJSONString(pTokenObject))
                .sign(Algorithm.HMAC256(serc));
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
            String payLoad = decodedJWT.getPayload();

            if(!decodedJWT.getSignature().equals(serc)){
                throw new AuthException("签名非法");
            }

            TokenObject tokenObject = JSON.parseObject(payLoad,TokenObject.class);
            LocalDateTime expire = tokenObject.getExpire();
            if(LocalDateTime.now().isAfter(expire)){
                throw new AuthException("token过期");
            }

            return tokenObject;
        }catch (Exception ex){
            throw new ServiceException("解析token时发生错误，错误信息是: "+ex.toString());
        }

    }
}
