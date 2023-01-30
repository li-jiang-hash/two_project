package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangxuhui
 * @since 2023-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UUserInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId
    private String id;

    /**
     * 用户头像
     */
    private String uicon;

    /**
     * 用户姓名
     */
    private String uname;

    /**
     * 0男1女
     */
    private Integer usex;

    /**
     * 用户地址
     */
    private String location;

    /**
     * 用户年龄
     */
    private Integer age;

    /**
     * 用户手机号
     */
    private String telephone;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 用户状态 0正常 1冻结 
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

    /**
     * 操作人
     */
    private String updateman;


}
