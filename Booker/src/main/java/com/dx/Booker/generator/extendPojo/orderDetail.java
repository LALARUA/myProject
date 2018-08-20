package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Books;
import com.dx.Booker.generator.po.Orderdetail;

public class orderDetail extends Orderdetail {
    Books book;
    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}
