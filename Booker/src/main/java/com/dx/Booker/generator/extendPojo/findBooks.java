package com.dx.Booker.generator.extendPojo;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 10:41 2018/7/12 0012
 */
public class findBooks {
    private String keyWord;
    private Integer pageNow;
    private String orderBy;
    private String method;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public Integer getPageNow() {
        return pageNow;
    }

    public void setPageNow(Integer pageNow) {
        this.pageNow = pageNow;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }
}
