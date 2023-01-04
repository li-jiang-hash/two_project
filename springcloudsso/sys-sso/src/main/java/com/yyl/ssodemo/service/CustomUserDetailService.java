package com.yyl.ssodemo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface CustomUserDetailService extends UserDetailsService {
    // 第一个参数  代表的是 认证的用户名   第二个参数 用于识别的标志列  loginType
    UserDetails loadUserByUsernameAndType(String var1,String var2) throws UsernameNotFoundException;
}
