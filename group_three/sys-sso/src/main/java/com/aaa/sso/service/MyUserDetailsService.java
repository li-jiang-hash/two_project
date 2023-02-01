package com.aaa.sso.service;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.TRole;
import com.aaa.entity.TUser;
import com.aaa.sso.feign.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 用户名
        EEmpInfo one = userService.getByUserName(s);
        if(one==null){
            System.out.println("用户名不对");
            throw  new UsernameNotFoundException("用户名不对");
        }
        // 根据用户的id 查询用户的角色

        List<String> roles = userService.getRoleList(one.getId());
        List<SimpleGrantedAuthority> roleList = roles.stream().map(tRole -> new SimpleGrantedAuthority("ROLE_"+tRole)).collect(Collectors.toList());

        // 查询资源
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
//        authorities.add(new SimpleGrantedAuthority("user:add"));
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return new User(s,one.getPassword(),roleList);
    }
}
