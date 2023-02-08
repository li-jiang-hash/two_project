package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * <p>
 * 管理员信息表
 * </p>
 *
 * @author zhangxuhui
 * @since 2023-01-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EEmpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 管理员id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 管理员名字
     */
    private String ename;

    /**
     * 入职时间
     */
    private LocalDateTime gmtCreate;

    /**
     * 更新时间
     */
    private LocalDateTime gmtModified;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 角色
     */
    private String rid;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 员工状态 0在职 1离职 2冻结
     */
    private Integer state;

    /**
     * 管理员密码
     */
    private String password;

    /**
     * 加盐
     */
    private String salt;

    /**
     * 管理的地区id
     */
    private String areaId;

    /**
     * 操作人
     */
    private String updatemanid;

    /**
     * 逻辑删除 1（true）已删除， 0（false）未删除
     */
    private Integer isDeleted;



}
