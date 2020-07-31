package com.lch.ssm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author lch
 * @create 2020-07-31 11:16
 */
@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private MyPasswordEncoder myPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() // 给请求设置权限
                .antMatchers("/security/admin/to/loginpage")
                .permitAll()
                .antMatchers("/bootstrap/**","/crowd/**",
                        "/css/**","/fonts/**","/img/**","/jquery/**",
                        "/layer/**","/script/**","/ztree/**") // 给静态资源设置访问权限
                .permitAll()
                .antMatchers("/WEB/INF/admin-manage.jsp")
                .hasRole("大师")
                .anyRequest()
                .permitAll()
                .and()

                .csrf()
                .disable()

                .formLogin()
                .loginPage("/security/admin/to/loginpage") // 去登录页面
                .loginProcessingUrl("/security/admin/do/login") // 登录请求的请求url
                .defaultSuccessUrl("/security/admin/to/mainpage",true) //登录成功的url
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")

                .and()
                .logout()
                .logoutUrl("/security/admin/to/logout")
                .logoutSuccessUrl("/security/admin/to/loginpage")
        ;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //内存版
//                .inMemoryAuthentication()
//                .withUser("tom").password("123").roles("ADMIN");

        // 数据库
            .userDetailsService(userDetailsService)
            .passwordEncoder(myPasswordEncoder)
        ;
    }
}
