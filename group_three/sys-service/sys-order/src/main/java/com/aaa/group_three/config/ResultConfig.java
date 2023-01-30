package com.aaa.group_three.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true)
public class ResultConfig extends ResourceServerConfigurerAdapter {

        @Override
    public void configure(HttpSecurity http) throws Exception{
            http.authorizeRequests().anyRequest().authenticated()
                    .and().csrf().disable();
        }
        //token 解密
    @Bean
    public JwtTokenStore getTokenStore(){
            return new JwtTokenStore(new JwtAccessTokenConverter());
    }
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        // 设置签名
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey("lijiang");
        return jwtAccessTokenConverter;
    }
}
