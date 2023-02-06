package com.aaa.sso.feign;

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
    @GetMapping("t-user/getUserByName/{username}")
    TUser getByUserName(@PathVariable String username);

    @GetMapping("t-role/{uid}")
    List<TRole> findByUid(@PathVariable Integer uid);
}
