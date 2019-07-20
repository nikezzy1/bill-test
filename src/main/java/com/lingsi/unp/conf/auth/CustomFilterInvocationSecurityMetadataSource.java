package com.lingsi.unp.conf.auth;

import com.lingsi.unp.mapper.auth.AppMenuMapper;
import com.lingsi.unp.model.auth.AppMenu;
import com.lingsi.unp.model.auth.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 获取请求url所需要的角色集合
 */
public class CustomFilterInvocationSecurityMetadataSource  implements FilterInvocationSecurityMetadataSource {
    AntPathMatcher antPathMatcher = new AntPathMatcher();
    @Autowired
    AppMenuMapper menuMapper;

    public static List<AppMenu> allMenus = null;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl = ((FilterInvocation) object).getRequestUrl();
        System.out.println("cuurent requestUrl:"+requestUrl);
        if(allMenus == null || allMenus.size() <= 0){
            allMenus = menuMapper.getAllMenus();
            System.out.println("allMenus="+allMenus.size());
        }

        ArrayList<String> roleList = new ArrayList<String>();
        if(allMenus!=null)
        for(AppMenu menu : allMenus){
            if(menu.getUrl() ==null){
                continue;
            }
            String[] urls = menu.getUrl().trim().split(",");
            if(urls!=null && urls.length>0) {
                for(String url : urls) {
                    System.out.println("start appear :"+url);
                    if (antPathMatcher.match(url, requestUrl)) {
                        System.out.println("matched menu:" + menu.getId() + " with url " + menu.getUrl() + "  -->" + menu.getRoles().get(0).getRoleid());
                        List<AppRole> roles = menu.getRoles();
                        //String[] roleAttr = new String[roles.size()];
                        for (int i = 0; i < roles.size(); i++) {
                            //roleAttr[i] = roles.get(i).getRoleid();
                            roleList.add(roles.get(i).getRoleid());
                        }

                        //return SecurityConfig.createList(roleAttr);
                    }
                }
            }
        }
        if(roleList.size()>0)
            return SecurityConfig.createList((String[]) roleList.toArray(new String[roleList.size()] ));

        System.out.println("match fail -->requestUrl:"+requestUrl+" need admin");
        return SecurityConfig.createList("admin");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    public static  void main(String[] args){
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String url="/api/contract/preview**";
        String requestUrl="/api/contract/preview?do=xa";
        if (antPathMatcher.match(url, requestUrl)) {
            System.out.println("ok");
        }else
            System.out.println("not ok");

    }
}
