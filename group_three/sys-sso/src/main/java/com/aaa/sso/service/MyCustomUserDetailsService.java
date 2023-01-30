package com.aaa.sso.service;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.UUserInfo;
import com.aaa.sso.feign.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyCustomUserDetailsService implements CustomUserDetailService{
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsernameAndType(String var1, String var2) throws UsernameNotFoundException {
        //
        System.out.println("var1 = " + var1);
        System.out.println("var2 = " + var2);
        // EMP_PHONE -> emp
        // USER_PHONE ->user
        // BUSSINESS_PHONE->bussiness
        if(var2.equals("u-user-info_PHONE")){
            // 查询 emp表   123    123456
           return loadUserByUsername1(var1);

        }else if(var2.equals("b-business-info_PHONE")){
            return loadUserByUsername(var1);
        }else if(var2.equals("e-emp-info_PHONE")){
            return loadUserByUsername2(var1);
        }
        return null;
    }

    private UserDetails  loadUserByUsername2(String var1) {
        EEmpInfo two=userService.getEmpByPhone(var1);
        System.out.println("数据库中的密码="+two.getPassword());
        System.out.println("telephone"+two.getTelephone());
        if(two==null){
            System.out.println("用户名不对");
            throw new UsernameNotFoundException("用户名不对");
        }
        return new User(var1, two.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));
    }

    private UserDetails loadUserByUsername1(String var1) {
        UUserInfo one=userService.getByPhone(var1);
        System.out.println("数据库中的密码="+one.getPassword());
        System.out.println("username"+one.getUname());
        if(one==null){
            System.out.println("用户名不对");
            throw new UsernameNotFoundException("用户名不对");
        }
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new User(var1,one.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));
    }




    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
