package com.aaa.sso.service;

import com.aaa.entity.EEmpInfo;
import com.aaa.entity.UUserInfo;
import com.aaa.sso.feign.UserService;
import com.aaa.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/*
 * @Author: 江Sir
 * @Date: 2023/02/03 13:32:55
 * @Description:
 * @Since version:  2022.10
 */
@Service
public class UserServiceImpl {
    @Resource
    private UserService userService;

    public Result insert(){



//       return userService.insert(UUserInfo userInfo);
        return new Result<>();
    }

}
