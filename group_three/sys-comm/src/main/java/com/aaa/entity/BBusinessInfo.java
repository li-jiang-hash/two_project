package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 商家信息表
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BBusinessInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 商家头像
     */
    private String bicon;

    /**
     * 店铺名
     */
    private String sname;

    /**
     * 商家身份证上显示的名字
     */
    private String bname;

    /**
     * 入驻时间
     */
    private String enterdate;

    /**
     * 营业执照
     */
    private String license;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 支付宝账号
     */
    private String ailiappid;

    /**
     * 状态 0营业 1休息 2整顿
     */
    private Integer state;

    /**
     * 身份证正面
     */
    private String cardfront;

    /**
     * 身份证反面
     */
    private String cardreverse;

    /**
     * 商家余额
     */
    private Double money;

    /**
     * 申请时间
     */
    private String applicationdate;

    /**
     * 通过或不通过原因
     */
    private String reason;

    /**
     * 审核人
     */
    private String checker;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 商家密码
     */
    private String password;

    /**
     * 盐
     */
    private String salt;

    /**
     * 营业类别 生鲜0 水果1 海鲜3 有机蔬菜4 
     */
    private String sortid;

    /**
     * 用户申请商家审核审核状态  2待审核 1驳回 0成功
     */
    private Integer status;

    /**
     * 店铺地址
     */
    private String address;
    @TableField(exist = false)
    private GSort gSort;

}
