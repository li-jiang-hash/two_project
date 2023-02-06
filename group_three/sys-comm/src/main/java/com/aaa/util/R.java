package com.aaa.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/*
 * @Author: 江Sir
 * @Date: 2023/02/04 11:16:21
 * @Description:
 * @Since version:  2022.10
 */
@Data
public class R {


    private  boolean success;
    private  Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<>();
    private R(){}

    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(200);
        r.setMessage("成功");
        return r;
    }
    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(500);
        r.setMessage("失败");
        return r;
    }
}
