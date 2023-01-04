package com.yyl.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    /**
     * 状态码
     */
    private Integer code=200;
    /**
     * 信息
     */
    private String msg="操作成功！！";

    /**
     * 数据
     */
    private T data;

    public Result(T t) {
        this.code=200;
        this.msg="操作成功！！";
        this.data = t;
    }
}