package indi.zhifa.learn.xdclass.busy.controller.filter;

import indi.zhifa.learn.xdclass.busy.eneity.dto.TokenObject;
import indi.zhifa.learn.xdclass.busy.util.TokenUtil;
import indi.zhifa.learn.xdclass.busy.util.WordsUtil;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
@AllArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    final TokenUtil mTokenUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws AuthException {
        String token = request.getHeader(WordsUtil.tokenKey);
        if(!StringUtils.hasLength(token)){
            throw new AuthException("token 为空");
        }
        TokenObject tokenObject = mTokenUtil.parseToken(token);
        mTokenUtil.memoToken(tokenObject);
    }





}
