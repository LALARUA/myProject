package com.dx.Booker.controller;

import com.dx.Booker.generator.extendPojo.*;
import com.dx.Booker.generator.po.*;
import com.dx.Booker.serviceinterface.AdminService;
import com.dx.Booker.serviceinterface.BookService;
import net.sf.json.JSONArray;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private BookService bookService;

    @ResponseBody
    @RequestMapping("/deleteComment")
    /**
     * @description 管理员删除评论
     * @author xiangXX
     * @date 2018/6/12 0012 11:27
      * @param supportMessage   删除评论所需信息
     * @param httpSession
     *
     */
    public Map deleteComment(supportMessage supportMessage, HttpSession httpSession) {
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            adminService.deleteUserComment(supportMessage, httpSession);
            info = "删除成功";
        } catch (Exception e) {
            info = "删除失败";
            e.printStackTrace();
        }
        data.put("info", info);
        return data;
    }


    @RequestMapping("/homepage")
    /**
     * @description 管理员主页
     * @author xiangXX
     * @date 2018/6/12 0012 11:28
      * @param
     *
     */
    public String adminHomepage() {

        return "adminHomepage";
    }

    @RequestMapping("/addBookPage")
    /**
     * @description 添加Book的页面
     * @author xiangXX
     * @date 2018/6/12 0012 11:28
      * @param
     *
     */
    public String addBook(Model model) {

        adminService.addBookPage(model);
        return "adminAddBookPage";
    }

    @RequestMapping("/handleTagsPage")
    public String handleTagsPage(Model model){

        adminService.handleTagsPage(model);
        return "adminHandleTag";
    }

    @ResponseBody
    @RequestMapping("/handleTags")
    public Map handleTags(handleTag handleTag,HttpSession httpSession){
        HashMap<Object, Object> data = new HashMap<>();
        adminService.handleTags(handleTag,httpSession);
        data.put("info","操作成功");
        return data;
    }
    @RequestMapping("/handleHotTag")
    public String handleHotTag(allTags allTags, HttpSession httpSession){
        List<Integer> tagIds = allTags.getTagIds();
        adminService.handleHotTag(tagIds,httpSession);
        return "redirect:/admin/handleTagsPage";

    }

    @RequestMapping("/addTags")
    public String addTags(handleTag handleTag,HttpSession httpSession){
        adminService.handleTags(handleTag,httpSession);
        return "redirect:/admin/handleTagsPage";
    }

    @PostMapping("/book")
    /**
     * @description 添加Book
     * @author xiangXX
     * @date 2018/6/12 0012 11:28
      * @param addBook
     * @param image
     * @param bindingResult
     *
     */
    public String addBook(addBook addBook, MultipartFile image, BindingResult bindingResult) throws Exception {
        adminService.addBook(addBook, image);
        return "adminAddBookPage";
    }

    @GetMapping("/book/{method}/{keyWord}/{orderBy}")
    /**
     * @description 查找Book
     * @author xiangXX
     * @date 2018/6/12 0012 11:28
      * @param method
     * @param condition
     * @param model
     *
     */
    public String findBooks(@PathVariable("orderBy") String orderBy,
            @PathVariable("method") Integer method, @PathVariable("keyWord") String keyWord, Model model) {
        HashMap<Object, Object> booksMap = new HashMap<>();
        List<Books> books = new ArrayList<>();
        findBooks findBooks = new findBooks();
        findBooks.setKeyWord(keyWord);
        findBooks.setOrderBy(orderBy);
        findBooks.setPageNow(1);

        if (method == 0) {
            return "adminFindBooks";

        }
        else if ((method == 1))
            booksMap = bookService.searchBook(findBooks);
        else if (method == 2)
            booksMap = bookService.findBooksByTitle(findBooks);

        else if (method == 3)
            booksMap = bookService.selectBookPageQueryByAuthor(findBooks);
        else if (method == 5)
            booksMap = bookService.selectBookPageQueryByTag(findBooks);
        else if (method == 4) {
            model.addAttribute("books", bookService.findBookByISBN(keyWord));
            model.addAttribute("pageNum", 1);
            return "adminFindBooks";
        }
        books = (ArrayList) booksMap.get("books");
        model.addAttribute("books", books);
        model.addAttribute("pageNum", booksMap.get("pageNum"));
        model.addAttribute("method", method);
        model.addAttribute("keyWord", keyWord);
        model.addAttribute("orderBy",orderBy);
        return "adminFindBooks";
    }

    @GetMapping("/handleOrders/{condition}")
    /**
     * @description 去到处理订单页面
     * @author xiangXX
     * @date 2018/6/12 0012 11:29
      * @param model
     * @param condition
     *
     */
    public String adminHandleOrders(Model model, @PathVariable("condition") String condition) {
        List<order> orders = null;

        if (condition.equals("sendOutBooks")) {
            orders = adminService.adminHandleOrders("支付成功");
            condition = "待发货订单";
        } else if (condition.equals("returnOrders")) {
            orders = adminService.adminHandleOrders("退货%中");
            condition = "退货订单";
        } else if (condition.equals("refundOrders")) {
            orders = adminService.adminHandleOrders("退款审核中");
            condition = "退款订单";
        }
        model.addAttribute("condition", condition);
        model.addAttribute("orders", orders);
        return "adminHandleOrders";
    }

    @ResponseBody
    @PostMapping("/ajaxHandleOrder")
    /**
     * @description 处理订单
     * @author xiangXX
     * @date 2018/6/12 0012 11:29
      * @param order
     * @param way
     *
     */
    public Map<Object, Object> sendOutBooks(Order order, String way) {
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            adminService.handleOrder(order, way);
            info = "操作成功";
        } catch (Exception e) {
            info = "操作失败";
            e.printStackTrace();
        }
        data.put("info", info);
        return data;
    }

    @ResponseBody
    @RequestMapping("/findBooksByPage")
    /**
     * @description 通过页数查找Book
     * @author xiangXX
     * @date 2018/6/12 0012 11:30
      * @param condition
     * @param pageNow
     * @param method
     *
     */
    public String findBookPage(findBooks findBooks) {
        List books = new ArrayList();

      Integer method = Integer.valueOf(findBooks.getMethod());
        Map map = null;
        if (method == 5) {
            map = bookService.selectBookPageQueryByTag(findBooks);


        } else if (method == 1) {
            map = bookService.searchBook(findBooks);

        } else if (method == 2) {
            map = bookService.findBooksByTitle(findBooks);

        } else if (method == 3) {
            map = bookService.selectBookPageQueryByAuthor(findBooks);
        }
        books = (ArrayList) map.get("books");
        JSONArray jsonArray = JSONArray.fromObject(books);
        return jsonArray.toString();
    }

    @ResponseBody
    @RequestMapping("/deleteBook")
    /**
     * @description 删除Book
     * @author xiangXX
     * @date 2018/6/12 0012 11:30
      * @param bookId
     *
     */
    public Map deleteBook(Integer bookId) {
        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            adminService.deleteBook(bookId);
            info = "删除成功";
        } catch (Exception e) {
            info = "删除失败";
            e.printStackTrace();
        }
        data.put("info", info);
        return data;
    }

    @GetMapping("/updateBookPage/{bookId}")
    /**
     * @description 更新Book页面
     * @author xiangXX
     * @date 2018/6/12 0012 11:30
      * @param bookId
     * @param model
     *
     */
    public String updateBookPage(@PathVariable("bookId") Integer bookId, Model model) {
        Books booksDetail = bookService.findBooksDetail(bookId);
        model.addAttribute("bookDetail", booksDetail);
        return "adminUpdateBookPage";

    }

    @RequestMapping("/updateBook")
    /**
     * @description 更新Book
     * @author xiangXX
     * @date 2018/6/12 0012 11:31
      * @param bookDetail
     *
     */
    public String updateBook(Books bookDetail) {

        adminService.updateBook(bookDetail);
        return "adminUpdateBookPage";
    }

    @RequestMapping("/updateBookImage")
    /**
     * @description 更新Book图片
     * @author xiangXX
     * @date 2018/6/12 0012 11:31
      * @param bookDetail
     * @param image
     * @param model
     *
     */
    public String updateBookImage(Books bookDetail, MultipartFile image, Model model) {
        adminService.updateBookImage(bookDetail, image);

        model.addAttribute("bookDetail", bookDetail);

        return "adminUpdateBookPage";
    }

    @RequestMapping("/updateDynamicInformation")
    public String updateDynamicInformation(DynamicInformations dynamicInformations,
                                           Model model, HttpServletRequest httpServletRequest){









        return "adminHomepage";
    }


}
