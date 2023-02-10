package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wanglei
 * @since 2023-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UComment implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 订单编号
     */
    private String code;

    /**
     * 评论信息
     */
    private String info;

    /**
     * 评论图片
     */
    private String img;

    /**
     * 等级评价(1-5)
     */
    private Integer level;

    /**
     * 评论时间
     */
    private LocalDateTime createtime;

    /**
     * 修改时间
     */
    private LocalDateTime updatetime;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;

    /**
     * 用户id
     */
    private String userid;

    /**
     * 商品id
     */
    private String goodsid;

    /**
     * 店铺id
     */
    private String bid;

    /**
     * 0正常 1封禁
     */
    private Integer status;

    @TableField(exist = false)
    private UUserInfo userInfo;
    @TableField(exist = false)
    private List imgs;

}
