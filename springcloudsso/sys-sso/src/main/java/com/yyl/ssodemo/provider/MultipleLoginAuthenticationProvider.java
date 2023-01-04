package com.yyl.ssodemo.provider;


import com.yyl.ssodemo.service.MyCustomUserDetailsService;
import com.yyl.ssodemo.token.MultipleLoginAuthenticationToken;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
public class MultipleLoginAuthenticationProvider  implements AuthenticationProvider {

    private MyCustomUserDetailsService myCustomUserDetailsService;

    @Getter
    private BCryptPasswordEncoder passwordEncoder;

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    @Getter
    @Setter
    private volatile String userNotFoundEncodedPassword;

    public MultipleLoginAuthenticationProvider() {

        passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MultipleLoginAuthenticationToken authenticationToken = (MultipleLoginAuthenticationToken) authentication;

        Map<String, String> details = (Map<String, String>) authenticationToken.getDetails();
        // 获取自定义的loginType参数
        String loginType = details.get("loginType");
        // 使用自定义的loginType在userDetailServceice中查询不同的数据库
        UserDetails user = this.myCustomUserDetailsService.loadUserByUsernameAndType(authentication.getName(),loginType);
        if (user == null) {
            throw new InternalAuthenticationServiceException(
                    "用户信息为空");
        }
        if (!"APP".equals(loginType)) {
            // app登录不校验密码
            // 获取当前输入的密码
            String presentedPassword = authentication.getCredentials().toString();
            if (!passwordEncoder.matches(presentedPassword, user.getPassword())) {
                log.error("用户名或密码错误,用户名:" + authentication.getName());
                throw new BadCredentialsException("用户名或密码错误");
            }
        }

        if (details.containsKey("password")) {
            details.put("password", null);
        }

        MultipleLoginAuthenticationToken authenticationResult = new MultipleLoginAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());

        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MultipleLoginAuthenticationToken.class.isAssignableFrom(authentication);
    }



    protected PasswordEncoder getPasswordEncoder() {
        return this.passwordEncoder;
    }



    protected MyCustomUserDetailsService getUserDetailsService() {
        return this.myCustomUserDetailsService;
    }


    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        Assert.notNull(passwordEncoder, "passwordEncoder cannot be null");

        this.passwordEncoder=passwordEncoder;
        this.userNotFoundEncodedPassword = null;
    }


    public void setUserDetailsService(MyCustomUserDetailsService myCustomUserDetailsService) {
        this.myCustomUserDetailsService = myCustomUserDetailsService;
    }
}

