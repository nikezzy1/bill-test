package com.lingsi.unp.conf.auth.filter;

import com.lingsi.unp.exception.ValidateCodeException;
import com.lingsi.unp.conf.auth.handler.MyAuthenticationFailureHandler;
import com.lingsi.unp.controller.auth.SystemLoginAuth;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 定义一个验证码的拦截器
 * @author hdd
 */
public class ValidateCodeFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException , ValidateCodeException {
        String method = request.getMethod().toUpperCase();
        String url = request.getRequestURI();
        System.out.println("method="+method+"&& url="+url);
        if ("/selflogin".equals(url) &&
                method.equalsIgnoreCase("post")) {
            try {
                validate(new ServletWebRequest(request));
            } catch (Exception e) {
                e.printStackTrace();
                MyAuthenticationFailureHandler myAuthenticationFailureHandler = new MyAuthenticationFailureHandler();
                myAuthenticationFailureHandler.onAuthenticationFailure(request,response,new ValidateCodeException(e.getMessage()));
                return;
            }
        }
        request.removeAttribute(SystemLoginAuth.verification);
        filterChain.doFilter(request,response);
    }

    //具体的验证流程
    private void validate(ServletWebRequest request) throws Exception {
        String verification = request.getParameter(SystemLoginAuth.verification);
        System.out.println("当前验证码为="+verification);
    }
}
