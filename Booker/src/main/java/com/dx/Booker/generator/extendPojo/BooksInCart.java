package com.dx.Booker.generator.extendPojo;

import com.dx.Booker.generator.po.Books;
import com.dx.Booker.generator.po.Cart;

public class BooksInCart extends Cart {
    Books books;

    public Books getBooks() {
        return books;
    }

    public void setBooks(Books books) {
        this.books = books;
    }
}
