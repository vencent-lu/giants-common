/**
 * 
 */
package com.giants.common.tools;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

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

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public  T getFilters() {
        if (this.filters == null) {
            Class<T> filterClass = null;
            Type type = this.getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                Type genericType = ((ParameterizedType)type).getActualTypeArguments()[0];
                if (genericType instanceof ParameterizedType) {
                    filterClass = (Class<T>)((ParameterizedType)genericType).getRawType();
                } else {
                    filterClass = (Class<T>)genericType;
                }
            }
            if (filterClass == null) {
                throw new NotFindFilterClassException();
            }
            
            if(filterClass.equals(Map.class)) {
                this.filters = (T)new HashMap();
            } else {
                try {
                    this.filters =filterClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new FilterClassNotFindNoArgumentsConstructorException(filterClass, e);
                }
            }            
        }
        return filters;
    }

    public void setFilters(T filters) {
        this.filters = filters;
    }
    
}
