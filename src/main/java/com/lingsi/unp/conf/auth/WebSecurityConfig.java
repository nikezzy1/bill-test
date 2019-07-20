package com.lingsi.unp.conf.auth;

import com.lingsi.unp.conf.auth.filter.ValidateCodeFilter;
import com.lingsi.unp.conf.auth.handler.MyAuthenticationFailureHandler;
import com.lingsi.unp.conf.auth.handler.MyAuthenticationSuccessHandler;
import com.lingsi.unp.conf.auth.handler.MyLogoutSuccessHandler;
import com.lingsi.unp.service.auth.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by sang on 2017/12/28.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AppUserService appUserService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        web.ignoring().antMatchers("/js/**");
        web.ignoring().antMatchers("/images/**");
        web.ignoring().antMatchers("/lib/**");
        web.ignoring().antMatchers("/fonts/**");
        web.ignoring().antMatchers("/lang/**");
        web.ignoring().antMatchers("/login/**");
        web.ignoring().antMatchers("/login.html");
        web.ignoring().antMatchers("/*.ico");
        //解决服务注册url被拦截的问题
        web.ignoring().antMatchers("/swagger-resources/**");
        web.ignoring().antMatchers("/swagger-ui.html");
        web.ignoring().antMatchers("/*/springfox-swagger-ui/**");
        web.ignoring().antMatchers("/v2/**");
        web.ignoring().antMatchers("/**/*.json");

        //账户激活接口
        web.ignoring().antMatchers("/selfactivate");
        web.ignoring().antMatchers("/selfactivate/sendmessagecode");

        web.ignoring().antMatchers("/notarial/**");


        //todo wuzu test
        web.ignoring().antMatchers("/test/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole("admin")
                //.antMatchers("/api/**").hasRole("user")
                //.antMatchers("/api/**").hasRole("user")
                .anyRequest().authenticated()
                .antMatchers("/selflogin*").permitAll()

                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(cfisms());
                        object.setAccessDecisionManager(cadm());
                        return object;
                    }
                })
                .and()
                .addFilterBefore(new ValidateCodeFilter(), UsernamePasswordAuthenticationFilter.class)//在UsernamePasswordAuthenticationFilter添加新添加的拦截器

                .formLogin()
                .loginProcessingUrl("/selflogin")
                .loginPage("/selflogin")
                .usernameParameter("userId").passwordParameter("passWord")
                .failureHandler(new MyAuthenticationFailureHandler())
                .successHandler(new MyAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/selflogout")
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and()
                .exceptionHandling().authenticationEntryPoint(loginUrlAuthenticationEntryPoint())
        ;
    }

    @Bean
    CustomFilterInvocationSecurityMetadataSource cfisms() {
        return new CustomFilterInvocationSecurityMetadataSource();
    }
    @Bean
    CustomeAccessDecisionManager cadm() {
        return new CustomeAccessDecisionManager();
    }
    @Bean
    public AuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/login");
    }
}