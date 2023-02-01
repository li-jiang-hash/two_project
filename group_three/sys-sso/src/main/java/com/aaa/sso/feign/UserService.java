package com.aaa.sso.feign;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.TRole;
import com.aaa.entity.TUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

// 调用的是哪一个微服务
@FeignClient("syssystem")
public interface UserService {

    // 根据用户名 获取TUser
    @GetMapping("/login/getUserByName/{username}")
    EEmpInfo getByUserName(@PathVariable String username);

    @GetMapping("/login/{eid}")
    List<String> getRoleList(@PathVariable Integer eid);
}
