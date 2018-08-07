package com.dx.Booker.serviceinterface;

import com.dx.Booker.generator.extendPojo.findBooks;
import com.dx.Booker.generator.po.Books;
import org.hibernate.validator.constraints.ISBN;

import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BookService {
    //按图书标签分页查询图书
    public HashMap selectBookPageQueryByTag(findBooks findBooks);

    public Map BooksInfo(findBooks findBooks);
    //查询图书
    public HashMap searchBook(findBooks findBooks);
    public HashMap selectBookPageQueryByAuthor(findBooks findBooks);
    public Books findBooksDetail(Integer id);
    public HashMap BooksInHomepage();
    public HashMap findBooksByTitle(findBooks findBooks);
    public List<Books> findBookByISBN(String ISBN);

}
