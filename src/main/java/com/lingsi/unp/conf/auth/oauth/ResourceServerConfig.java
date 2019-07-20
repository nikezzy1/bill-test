package com.lingsi.unp.conf.auth.oauth;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

//@Configuration
//@EnableResourceServer
public class ResourceServerConfig
        extends ResourceServerConfigurerAdapter {
    private static final String RESOURCE_ID = "rid";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
               /* .antMatchers("/admin/**").hasRole("admin")
                .antMatchers("/user/**").hasRole("user")*/
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(new MyOAuth2AccessDeniedHandler());
    }
}
