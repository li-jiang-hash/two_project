package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author wanglei
 * @since 2023-02-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品主键	
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 商品名称
     */
    private String gname;

    /**
     * 商品图片
     */
    private String img;

    /**
     * 商品描述
     */
    private String remark;

    /**
     * 商品品牌
     */
    private Integer brandid;

    /**
     * 商品类别
     */
    private Integer sortid;

    /**
     * 储藏方式
     */
    private Integer depositid;

    /**
     * 商品单位
     */
    private Integer unitid;

    /**
     * 上架时间
     */
    private LocalDateTime addtime;

    /**
     * 保质期
     */
    private LocalDateTime expirationtime;

    /**
     * 商品状态0审核通过 1驳回 2待审核
     */
    private Integer state;

    /**
     * 上下架 0上架 1下架
     */
    private Integer status;

    /**
     * 商家id
     */
    private String bid;

    /**
     * 价格
     */
    private String price;

    /**
     * 逻辑删除
     */
    private Integer isdeleted;

    /**
     * 驳回理由
     */
    private String checkContent;


}
