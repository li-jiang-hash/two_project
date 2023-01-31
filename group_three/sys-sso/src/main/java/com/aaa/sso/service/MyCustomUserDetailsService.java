package com.aaa.sso.service;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.UUserInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class MyCustomUserDetailsService implements CustomUserDetailService{
    @Autowired
    private IEEmpInfoService ieEmpInfoService;
    @Autowired
    private IUUserInfoService iuUserInfoService;

    @Override
    public UserDetails loadUserByUsernameAndType(String username, String type) throws UsernameNotFoundException {
        //
        System.out.println("username = " + username);
        System.out.println("type = " + type);
        // EMP_PHONE -> emp
        // USER_PHONE ->user
        // BUSSINESS_PHONE->bussiness
        if(type.equals("EMP_PHONE")){
            // 查询 emp表   123    123456
           return loadUserByUsername1(username);

        }else if(type.equals("USER_PHONE")){
            return loadUserByUsername2(username);
        }
        return null;
    }
    private UserDetails loadUserByUsername1(String username) {
        QueryWrapper<EEmpInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("telephone",username);
        if(ieEmpInfoService.list(queryWrapper).size() > 1){
            System.out.println("用户名异常");
            throw  new UsernameNotFoundException("用户名异常");
        } else if (ieEmpInfoService.list(queryWrapper).size() == 0) {
            throw  new UsernameNotFoundException("用户名不对");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return new User(username,passwordEncoder.encode("123456"), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_emp,ROLE_admin"));
    }
    private UserDetails loadUserByUsername2(String username) {
        QueryWrapper<UUserInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("telephone",username);
        if(iuUserInfoService.list(queryWrapper).size() > 1){
            System.out.println("用户名异常");
            throw  new UsernameNotFoundException("用户名异常");
        } else if (iuUserInfoService.list(queryWrapper).size() == 0) {
            throw  new UsernameNotFoundException("用户名不对");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password="";
        return new User(username,passwordEncoder.encode(password), AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_user"));
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}
