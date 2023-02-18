package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 商品计量单位
 * </p>
 *
 * @author wanglei
 * @since 2023-02-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品单位id
     */
    private Integer id;

    /**
     * 商品单位名称
     */
    private String unitname;


}
