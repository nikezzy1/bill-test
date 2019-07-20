package com.lingsi.unp.conf.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lingsi.unp.mapper.auth.AppMenuMapper;
import com.lingsi.unp.model.auth.AppRole;
import com.lingsi.unp.model.auth.AppUser;
import com.lingsi.unp.model.response.CommonRes;
import com.lingsi.unp.utils.auth.UserUtils;
import com.lingsi.unp.utils.base.SpringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        Authentication auth) throws IOException {
        System.out.println("verification="+req.getParameter("verification"));
        resp.setContentType("application/json;charset=utf-8");
        AppUser appuser = UserUtils.getCurrentUser();
        if(appuser.getRoles()!=null && appuser.getRoles().size()>0)
            for(AppRole role : appuser.getRoles() ){
                role.setMenusList(SpringUtils.getBean(AppMenuMapper.class).getAaccessibleMenusByRoleId(role.getRoleid()));
            }
        CommonRes respBean = CommonRes.http_ok("登录成功!",appuser).success();
        ObjectMapper om = new ObjectMapper();
        PrintWriter out = resp.getWriter();
        out.write(om.writeValueAsString(respBean));
        out.flush();
        out.close();
    }
}
