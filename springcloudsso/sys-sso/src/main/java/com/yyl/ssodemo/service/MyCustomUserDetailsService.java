package com.yyl.ssodemo.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MyCustomUserDetailsService implements CustomUserDetailService{
    @Override
    public UserDetails loadUserByUsernameAndType(String var1, String var2) throws UsernameNotFoundException {
        //
        System.out.println("var1 = " + var1);
        System.out.println("var2 = " + var2);
        // EMP_PHONE -> emp
        // USER_PHONE ->user
        // BUSSINESS_PHONE->bussiness
        if(var2.equals("EMP_PHONE")){
            // 查询 emp表   123    123456
           return loadUserByUsername1(var1);

        }else if(var2.equals("USER_PHONE")){
            return loadUserByUsername2(var1);
        }
        return null;
    }

    private UserDetails loadUserByUsername2(String var1) {
        if(!var1.equals("123")){
            System.out.println("用户名不对");
            throw  new UsernameNotFoundException("用户名不对");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new User(var1,passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));
    }

    private UserDetails loadUserByUsername1(String var1) {
        if(!var1.equals("110")){
            System.out.println("用户名不对");
            throw  new UsernameNotFoundException("用户名不对");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new User(var1,passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_emp,ROLE_admin"));
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
