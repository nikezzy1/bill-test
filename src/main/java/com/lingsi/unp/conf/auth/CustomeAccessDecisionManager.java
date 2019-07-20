package com.lingsi.unp.conf.auth;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomeAccessDecisionManager implements AccessDecisionManager {
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> ca) throws AccessDeniedException, InsufficientAuthenticationException {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();//得到当前用户的权限
        for(ConfigAttribute configAttribute : ca){
            if("ROLE_LOGIN".equals(configAttribute.getAttribute())
                    && auth instanceof UsernamePasswordAuthenticationToken){//说明当前用户已经登陆
                return;
            }
            for(GrantedAuthority authority : authorities){
                System.out.println("need auth："+configAttribute.getAttribute());
                System.out.println("current auth："+authority.getAuthority());
                if(configAttribute.getAttribute().equals(authority.getAuthority())){
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
