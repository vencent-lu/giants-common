package com.giants.common.tools;

import java.io.Serializable;
import java.util.Collection;

/**
 * <pre>
 *  用来在分页查询，其作用是将当前查询的某一页的信息
 *  另外，也提供了转载用户序列化对象的把手
 * </pre>
 *
 * @author vencent.lu
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 3200069927678126482L;

    private int pageNo = 1;

    private int pageSize = 0;

    private int total = 0;

    private Collection<T> results;

    private int pageNumCount = -1;

    private String actionUrl;

    public Page() {
    }

    /**
     * @param pageNo 页码
     * @param pageSize 每页条数
     */
    public Page(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    /**
     * @param pageNo 页码
     * @param pageSize 每页条数
     * @param total 总数
     */
    public Page(int pageNo, int pageSize, int total) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
    }

    /**
     * @param pageNo      页码
     * @param pageSize    每页大小
     * @param total       总共大小
     * @param results 取得的当页的对象
     */
    public Page(int pageNo, int pageSize, int total, Collection<T> results) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.total = total;
        this.results = results;
    }

    public int getTotalPage() {
        if (pageSize <= 0)
            return 1;
        int totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        totalPage = total == 0 ? 1 : totalPage;
        return totalPage;
    }

    /**
     * 返回偏移量，如果越界，则返回最后一页的偏移量
     *
     * @return int
     */
    public int getOffset() {
        if (total == 0) {
            return 0; //(pageNo - 1) * pageSize;
        }
        if ((pageNo - 1) * pageSize >= total) {
            pageNo = getTotalPage();
        }
        return (pageNo - 1) * pageSize;
    }

    public int getPageNo() {
        if (pageNo > this.getTotalPage()) {
            return this.getTotalPage();
        } else if (pageNo < 1) {
            return 1;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    /**
     * @return the pageNumCount
     */
    public int getPageNumCount() {
        return pageNumCount;
    }

    /**
     * @param pageNumCount the pageNumCount to set
     */
    public void setPageNumCount(int pageNumCount) {
        this.pageNumCount = pageNumCount;
    }

    /**
     * @return the actionUrl
     */
    public String getActionUrl() {
        return actionUrl;
    }

    /**
     * @param actionUrl the actionUrl to set
     */
    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

}
