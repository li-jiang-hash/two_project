package com.aaa.sso.feign;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.UUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// 调用的是哪一个微服务
@FeignClient("sys-system")
public interface UserService {
    //根据用户名获取UUser
    @PostMapping("sys-sso/getUserByName/{username}")
    UUserInfo getByName(@PathVariable String username);
    //根据用户手机号查询用户信息
    @PostMapping("sys-sso/getUserByPhone/{telephone}")
    UUserInfo getByPhone(@PathVariable String telephone);
    @PostMapping("e-emp-info/getEmpByPhone/{telephone}")
    EEmpInfo getEmpByPhone(@PathVariable String telephone);
}
