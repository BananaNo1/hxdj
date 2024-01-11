package com.leis.hxds.common.util;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageUtils implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页面
     */
    private int pageIndex;
    /**
     * 列表数据
     */
    private List list;

    public PageUtils(long totalCount, int pageSize, int pageIndex, List list) {
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
        this.pageIndex = pageIndex;
        this.list = list;
    }
}
