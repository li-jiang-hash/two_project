package com.aaa.sso.service;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.UUserInfo;
import com.aaa.sso.feign.UserService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyCustomUserDetailsService implements CustomUserDetailService{

    @Resource
    private UserService userService;
    @Override
    public UserDetails loadUserByUsernameAndType(String var1, String var2) throws UsernameNotFoundException {
//        System.out.println("var1 = " + var1);
//        System.out.println("var2 = " + var2);
        // EMP_PHONE -> emp
        // USER_PHONE ->user
        // BUSSINESS_PHONE->bussiness
        System.out.println(var2);
        if(var2.equals("EMP_PHONE")){
            // 查询 emp表   123    123456
           return loadUserByUsername1(var1);

        }else if(var2.equals("USER_PHONE")){
            return loadUserByUsername2(var1);
        }
        return null;
    }
    /**
     * 查询的是e_emp_info
     * @param var1
     * @return
     */
    private UserDetails loadUserByUsername1(String var1) {
        System.out.println("var1 = " + var1);
        EEmpInfo emp = userService.getByUserName(var1);
        if(emp==null){
            System.out.println("商户不对");
            System.out.println(var1);
            throw  new UsernameNotFoundException("商户不对");
        }
        System.out.println("emp.getId() = " + emp.getId());
        List<String> byUid = userService.getRoleList(emp.getId());
        List<SimpleGrantedAuthority> collect = byUid.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());

        return new User(var1,emp.getPassword(), collect);
    }

    private UserDetails loadUserByUsername2(String telephone) {
        System.out.println("telephone = " + telephone);
        UUserInfo user = userService.getUserByPhone(telephone);
        if(user==null){
            System.out.println("用户名不对");
            System.out.println(telephone);
            throw  new UsernameNotFoundException("用户名不对");
        }
        return new User(telephone,user.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
