package com.dx.Booker.generator.extendPojo;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 9:01 2018/6/10 0010
 */
public class selectBooksOrderBy {
    private String condition;
    private Integer pageStart;

    public selectBooksOrderBy(String condition,Integer pageStart){
        this.condition = condition;
        this.pageStart = pageStart;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }
}
