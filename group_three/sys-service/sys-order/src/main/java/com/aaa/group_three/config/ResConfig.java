package com.aaa.group_three.config;
//

import com.aaa.util.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/t-user/**","/t-role/**","/o-cart/**","/o-order/**").permitAll()
                .anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedHandler(getAccessDefined())
                .and().csrf().disable();
    }

    // 没有权限
    private AccessDeniedHandler getAccessDefined() {
        return (httpServletRequest, response, e) -> {
            Result result = new Result(403,"没有权限",null);
            printJsonData(response,result);
        };
    }

    public  void printJsonData(HttpServletResponse response, Result result) throws IOException {
        // 返回认证成功.
        response.setContentType("application/json;charset=utf8");
        PrintWriter writer = response.getWriter();

        ObjectMapper objectMapper = new ObjectMapper();
        String s1 = objectMapper.writeValueAsString(result);
        writer.print(s1);
        writer.flush();
        writer.close();
    }
    // token  解密
    @Bean
    public JwtTokenStore getTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }


    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        // 设置签名
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("yuyongli");
        return jwtAccessTokenConverter;
    }

}
