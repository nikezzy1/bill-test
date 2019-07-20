package com.lingsi.unp.conf.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingsi.unp.exception.ValidateCodeException;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.model.response.CommonResEnum;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
                                        AuthenticationException e) throws IOException {
        resp.setContentType("application/json;charset=utf-8");
        CommonRes respBean = null;
        if (e instanceof BadCredentialsException ||
                e instanceof UsernameNotFoundException) {
            respBean = CommonRes.http_autherror("账户名或者密码输入错误!").fail();
        } else if (e instanceof LockedException) {
            respBean = CommonRes.http_autherror("账户被锁定，请联系管理员!").fail();
        } else if (e instanceof CredentialsExpiredException) {
            respBean = CommonRes.http_autherror("密码过期，请联系管理员!").fail();
        } else if (e instanceof AccountExpiredException) {
            respBean = CommonRes.http_autherror("账户过期，请联系管理员!").fail();
        } else if (e instanceof DisabledException) {
            respBean = CommonRes.http_autherror("账户未激活，请激活账户!").fail(CommonResEnum.DISNABLE);
        } else if (e instanceof ValidateCodeException){
            respBean = CommonRes.http_autherror("验证码错误，请仔细核对!");
        } else {
            respBean = CommonRes.http_error("登录失败!");
        }
        resp.setStatus(401);
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
