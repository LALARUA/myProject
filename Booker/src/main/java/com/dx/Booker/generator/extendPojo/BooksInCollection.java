package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Books;

/**
 * @Author: xiangXX
 * @Description:
 * @Date Created in 9:46 2018/6/10 0010
 */
public class BooksInCollection extends Books {
    private Integer collectDetailId;

    public Integer getCollectDetailId() {
        return collectDetailId;
    }

    public void setCollectDetailId(Integer collectDetailId) {
        this.collectDetailId = collectDetailId;
    }
}
