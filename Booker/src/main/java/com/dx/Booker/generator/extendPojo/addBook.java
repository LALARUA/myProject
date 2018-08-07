package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Books;

import java.util.List;

public class addBook extends Books {
    List<String> bookTags;

    public List<String> getBookTags() {
        return bookTags;
    }

    public void setBookTags(List<String> bookTags) {
        this.bookTags = bookTags;
    }
}
