package com.aaa.util;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageInfo extends Page {
    /**
     * 当前页
     */

    private Integer currentPage = 1;

    /**
     * 每页显示条数
     */
    private Integer pageSize = 10 ;

    public PageInfo(Integer currentPage, Integer pageSize){
        super(currentPage,pageSize);
    }
}
