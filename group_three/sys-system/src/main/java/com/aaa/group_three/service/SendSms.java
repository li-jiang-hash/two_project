package com.aaa.group_three.service;

import java.util.Map;

/*
 * @Author: 江Sir
 * @Date: 2023/1/4 23:33:37
 * @Description:
 * @Since version:  2022.10
 */
public interface SendSms {
    boolean send(String phoneNum, String templateCode, Map<String,Object> code);
}
