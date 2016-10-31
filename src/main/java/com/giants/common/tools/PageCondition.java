/**
 * 
 */
package com.giants.common.tools;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.giants.common.lang.StringUtil;

/**
 * <pre>
 *  用来在分页查询，查询条件对象
 * </pre>
 * @author vencent.lu
 *
 */
public class PageCondition implements Serializable{
	private static final long serialVersionUID = 5227230047364454683L;
	
	private Map<String, Object> filters;

	protected Integer pageNo;

    protected Integer pageSize;

    protected String orderBy;

    protected String orderSort;
    
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

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void setFilters(Map<String, Object> filters) {
		this.filters = filters;
	}
	
	public void put(String key, Object value){
		if (this.filters == null) {
			this.filters = new HashMap<String, Object>();
		}
		this.filters.put(key, value);
	}

}
