/**
 *
 */
package com.giants.common.tools;

import java.io.Serializable;

import com.giants.common.lang.StringUtil;

/**
 * @author vencent.lu
 *
 */
public class PageQueryCondition<T> implements Serializable {
    private static final long serialVersionUID = -6504219399633814605L;

    private T filters;

    private Integer pageNo;

    private Integer pageSize;

    private String orderBy;

    private String orderSort;

    protected PageQueryCondition() {
        super();
    }

    public PageQueryCondition(T filters) {
        super();
        this.filters = filters;
        this.pageNo = 1;
        this.pageSize = 10;
    }

    public PageQueryCondition(T filters, Integer pageNo, Integer pageSize) {
        super();
        this.filters = filters;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public PageQueryCondition(T filters, Integer pageNo, Integer pageSize, String orderBy, String orderSort) {
        super();
        this.filters = filters;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.orderSort = orderSort;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getStartOffSet() {
        return (pageNo - 1) * pageSize;
    }

    public Integer getEndOffSet() {
        return pageSize;
    }

    public String getOrderBy() {
        if (this.orderBy == null) {
            return null;
        }
        return StringUtil.camelToUnderline(orderBy);
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    public T getFilters() {
        return this.filters;
    }

    public void setFilters(T filters) {
        this.filters = filters;
    }

}
