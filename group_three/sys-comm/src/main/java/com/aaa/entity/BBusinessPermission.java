package com.aaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author wpp
 * @since 2023-01-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BBusinessPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商家权限id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 权限名
     */
    private String pname;

    /**
     * 权限路径
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 状态 0 正常 1 禁用
     */
    private Integer state;

    /**
     * 路由路径
     */
    private String path;


}
