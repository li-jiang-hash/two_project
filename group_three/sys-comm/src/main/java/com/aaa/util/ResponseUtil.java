package com.aaa.util;

import cn.hutool.http.HttpStatus;
import cn.hutool.http.server.HttpServerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * @Author: 江Sir
 * @Date: 2023/02/04 11:21:30
 * @Description:
 * @Since version:  2022.10
 */
public class ResponseUtil {
    public static void out(HttpServerResponse response, R r) {
        ObjectMapper mapper = new ObjectMapper();
//        response.setStatus(HttpStatus.HTTP_OK.)

    }
}
