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
 * @author yuyongli
 * @since 2022-12-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单的名字
     */
    private String mname;

    /**
     * 0 代表的是一级菜单
     */
    private Integer pid;

    /**
     * 给菜单指定一个路径
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    private String mcode;


}
