package com.aaa.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 左侧菜单地址
 * </p>
 *
 * @author yuyongli
 * @since 2022-12-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class EPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

    /**
     * 父级id
     */
    private Integer pid;

    /**
     * 权限名字
     */
    private String pname;

    /**
     * 链接
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 子菜单
     * children 不是列名
     *
     * 不参与查询
     * @TableField
     */
    @TableField(exist = false)
    private List<EPermission> children = new ArrayList<>();

}
