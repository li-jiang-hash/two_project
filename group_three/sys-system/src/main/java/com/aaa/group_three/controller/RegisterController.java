package com.aaa.group_three.controller;


import com.aaa.entity.UUserInfo;
import com.aaa.group_three.service.IUUserInfoService;
import com.aaa.util.Result;
import com.aaa.util.Send;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/*
 * @Author: 江Sir
 * @Date: 2023/1/6 23:49:04
 * @Description:
 * @Since version:  2022.10
 */
@RestController
@RequestMapping({"/user"})
public class RegisterController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    IUUserInfoService iuUserInfoService;

    public RegisterController() {
    }

    ///注册方法
    @PostMapping({"/signInsert/{phone}/{password}"})
    public Result Register(@PathVariable String phone, @PathVariable String password) {
//        Map<String, String> Key = PassTools.makePasswordSalt(password);
//        String pass = Key.get("password");
//        String salt = Key.get("salt");
        QueryWrapper<UUserInfo> queryWrapper = new QueryWrapper();
        queryWrapper.eq("telephone", phone);
        int list = iuUserInfoService.list(queryWrapper).size();
        if (list == 0) {
            UUserInfo uUserInfo = new UUserInfo();
            System.out.println(LocalDateTime.now());
            uUserInfo.setCreatetime(LocalDateTime.now());
            uUserInfo.setTelephone(phone);
            uUserInfo.setStatus(0);uUserInfo.setPassword(new BCryptPasswordEncoder().encode(password));
            uUserInfo.setSalt("123");
            boolean save = iuUserInfoService.saveOrUpdate(uUserInfo);
            Result result;
            if (save) {
                result = new Result("创建用户成功！");
                result.setMsg("用户注册成功！");
                return result;
            } else {
                result = new Result();
                result.setCode(4001);
                result.setMsg("送失败，请稍后重新尝试！");
                System.out.println("\"添加失败\" = 添加失败");
                return result;
            }
        } else {
            Result<Object> result = new Result();
            result.setCode(400);
            result.setMsg("用户已注册,请登录！");
            return result;
        }
    }

    @Value("${com.aliyun.AccessKey_ID}")
    private String AccessKey_ID;
    @Value("${com.aliyun.AccessKey_Secret}")
    private String AccessKey_Secret;
    @Value("${com.aliyun.templateCode3}")
    private String templateCode;

    //手机号发送验证码
    @GetMapping({"/noteByPhone/{phone}"})
    public Result KeyCode(@PathVariable String phone) throws Exception {
        String RCode = redisTemplate.opsForValue().get(phone);
        if (!StringUtils.isEmpty(RCode)) {
            return new Result(RCode);
        } else {
            String code = Integer.toString((int) ((Math.random() * 9 + 1) * 100000));
            boolean isSend = Send.SendCode(AccessKey_ID, AccessKey_Secret, templateCode, phone, code);
            if (isSend) {
                redisTemplate.opsForValue().set(phone, code, 300L, TimeUnit.SECONDS);
                return new Result(code);
            } else {
                return new Result();
            }
        }
    }
}
