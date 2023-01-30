package com.aaa.sso.config;


import com.aaa.sso.provider.MultipleLoginAuthenticationProvider;
import com.aaa.sso.provider.MyDaoAuthenricationProvider;
import com.aaa.sso.service.MyCustomUserDetailsService;
import com.aaa.util.Result;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyCustomUserDetailsService userDetailsService;


    @Resource
    private MultipleLoginAuthenticationSecurityConfig multipleLoginAuthenticationSecurityConfig;



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //auth.userDetailsService(userDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider());
        auth.authenticationProvider(customAuthenticationProvider2());
    }

    // 获取token的时候使用的
    private AuthenticationProvider daoAuthenticationProvider() {
        MyDaoAuthenricationProvider daoAuthenticationProvider = new MyDaoAuthenricationProvider();
        daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    // 认证的时候使用的
    public AuthenticationProvider customAuthenticationProvider2() {
        MultipleLoginAuthenticationProvider customAuthenticationProvider2 = new MultipleLoginAuthenticationProvider();
        //System.out.println("设置的service路径**********。。。。。");
        customAuthenticationProvider2.setUserDetailsService(userDetailsService);
        customAuthenticationProvider2.setPasswordEncoder(getPasswordEncoder());
        return customAuthenticationProvider2;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.apply(multipleLoginAuthenticationSecurityConfig);
        //
        http.formLogin()
//               .successHandler(getSuccessHandler())
//                .failureHandler(getFailtureHandler())
//                .permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(getLogOut()).permitAll() // 退出路径是/logout 成功 index.html
                //.and().authorizeRequests().antMatchers("/my/**").hasAnyRole("admin2","admin")//.hasAnyAuthority("admin","admin2")
                .and().exceptionHandling().accessDeniedHandler(getAccessDefined())// 403异常的时候
                .and()
                .authorizeRequests().antMatchers("/","/login").permitAll()
                .anyRequest().authenticated() // 除去上面的你写路径其他的路径全部需要认证
                .and().cors()// 允许跨域
                .and()
                .csrf().disable();

    }

    /**
     * 权限不足
     * @return
     */
    private AccessDeniedHandler getAccessDefined() {
        return (httpServletRequest, response, e) -> {
            Result result = new Result(403,"没有权限",null);
            printJsonData(response,result);
        };
    }

    /**
     * 退出成功之后的handler
     * @return
     */

    private LogoutSuccessHandler getLogOut() {
        return (httpServletRequest, response, authentication) -> {
            Result result = new Result(200,"退出成功",null);
            printJsonData(response,result);
        };
    }



    public  void printJsonData(HttpServletResponse response,Result result) throws IOException {
        // 返回认证成功.
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();
        String s1 = objectMapper.writeValueAsString(result);
        writer.print(s1);
        writer.flush();
        writer.close();
    }


    @Bean
    public BCryptPasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 认证管理器 注入到spring容器里面
     * @return
     * @throws Exception
     */

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(daoAuthenticationProvider());
        providers.add(customAuthenticationProvider2());

        return new ProviderManager(providers);
    }

    public Map<String, Object> json2map(String jsonString) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.readValue(jsonString, Map.class);
    }
}
