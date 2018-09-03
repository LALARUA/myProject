package com.dx.Booker.controller;

import com.dx.Booker.generator.extendPojo.commentAndSupport;
import com.dx.Booker.generator.extendPojo.findBooks;
import com.dx.Booker.generator.mytool.MyTool;
import com.dx.Booker.generator.po.*;
import com.dx.Booker.serviceinterface.BookService;
import com.dx.Booker.serviceinterface.CommentSevice;
import com.dx.Booker.serviceinterface.UserService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequestMapping("/book")
@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CommentSevice commentSevice;

    @Autowired
    private UserService userService;

    @GetMapping("/findBooks/{method}/{keyWord}/{orderBy}")
    /**
     * @description 查找图书
     * @author xiangXX
     * @date 2018/6/12 0012 11:31
     * @param condition
     * @param model
     * @param method
     * @param httpSession
     *
     */
    public String findBooks(@PathVariable("orderBy") String orderBy,@PathVariable("keyWord") String keyWord, Model model, @PathVariable("method") String method, HttpSession httpSession) {
        List<Books> books = new ArrayList<Books>();

        findBooks findBooks = new findBooks();
        findBooks.setKeyWord(keyWord);
        findBooks.setOrderBy(orderBy);
        findBooks.setPageNow(1);
        findBooks.setMethod(method);
        int pageNum = 0;
        Map map = null;
       map = bookService.BooksInfo(findBooks);
        User user = (User) httpSession.getAttribute("user");
        List<Collect> collects = new ArrayList<>();
        if (user != null)
            collects = userService.myCollect(user.getId());
        model.addAttribute("collects", collects);
        books = (ArrayList<Books>) map.get("books");
        for (Books book:books
             ) {book.setAuthor(MyTool.handleBookAuthor(book.getAuthor()));

        }
        pageNum = (Integer) map.get("pageNum");
        model.addAttribute("books", books);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("method", method);
        model.addAttribute("orderBy",orderBy);
        model.addAttribute("keyWord",keyWord);
        return "booksPage";
    }

    @GetMapping("/bookDetail/{bookId}")
    /**
     * @description 查看Book详情
     * @author xiangXX
     * @date 2018/6/12 0012 11:31
      * @param bookId
     * @param model
     * @param httpSession
     *
     */
    public String bookDetail(@PathVariable("bookId") int bookId, Model model, HttpSession httpSession) {
        Books book = bookService.findBooksDetail(bookId);
        User user = (User) httpSession.getAttribute("user");
        Integer userId = user.getId();
        List<commentAndSupport> commentAndSupports = commentSevice.selectCommentAndSupport(bookId, userId);

        List<Collect> collects = new ArrayList<>();
        if (user != null)
            collects = userService.myCollect(user.getId());
        commentAndSupport userComment = null;
        for (int i = 0;i<commentAndSupports.size();i++){
        if (commentAndSupports.get(i).getUserId()==userId)
        {
            userComment=commentAndSupports.get(i);
            break;
        }

     }
        model.addAttribute("userComment",userComment);
        model.addAttribute("collects", collects);
        model.addAttribute("book", book);
        model.addAttribute("commentAndSupports", commentAndSupports);
        return "bookDetail";
    }


    @ResponseBody
    @RequestMapping("/findBooksByPage")
    /**
     * @description 通过页数查找Book
     * @author xiangXX
     * @date 2018/6/12 0012 11:32
      * @param condition
     * @param pageNow
     * @param method
     *
     */
    public String findpage(findBooks findBooks) {
        List books = new ArrayList();


        Map map = null;

        map = bookService.BooksInfo(findBooks);

        books = (ArrayList) map.get("books");

        JSONArray jsonArray = JSONArray.fromObject(books);
        return jsonArray.toString();
    }


}
