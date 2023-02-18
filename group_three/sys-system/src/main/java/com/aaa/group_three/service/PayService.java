package com.aaa.group_three.service;

import com.aaa.entity.OOrder;
import com.aaa.entity.Order;

import java.util.Map;

/*
 * @Author: 江Sir
 * @Date: 2023/02/17 19:01:11
 * @Description:
 * @Since version:  2022.10
 */
public interface PayService {
    Map getNativeByOrder(Order order);
    Map getPayStatus(OOrder order);
}
