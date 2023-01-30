package com.aaa.sso.service;


import com.aaa.entity.UUserInfo;
import com.aaa.sso.feign.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 用户名
        UUserInfo one = userService.getByName(s);
        if(one==null){
            System.out.println("用户名不对");
            throw  new UsernameNotFoundException("用户名不对");
        }
        // 根据用户的id 查询用户的角色

        //List<UUserInfo> roles = userService.findByUid(one.getId());
        //List<SimpleGrantedAuthority> roleList = roles.stream().map(tRole -> new SimpleGrantedAuthority("ROLE_"+tRole.getRname())).collect(Collectors.toList());

        // 查询资源
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        authorities.add(new SimpleGrantedAuthority("user:add"));
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new User(s,one.getPassword(),authorities);
    }
}
