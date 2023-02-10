package com.aaa.entity;

import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class UAddr implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 发货地址
     */
    private String addr;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 逻辑删除
     */
    private Integer isDeleted;


}
