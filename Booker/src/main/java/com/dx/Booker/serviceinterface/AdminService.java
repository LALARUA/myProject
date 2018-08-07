package com.dx.Booker.serviceinterface;

import com.dx.Booker.generator.extendPojo.addBook;
import com.dx.Booker.generator.extendPojo.handleTag;
import com.dx.Booker.generator.extendPojo.order;
import com.dx.Booker.generator.extendPojo.supportMessage;
import com.dx.Booker.generator.po.Books;
import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.Order;
import com.dx.Booker.generator.po.tag;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.awt.image.VolatileImage;
import java.util.List;

public interface AdminService {
    public void addBook(addBook addBook, MultipartFile image);
    public void deleteBook(Integer bookId);
    public void updateBook(Books books);
    public void updateBookImage(Books books,MultipartFile image);
    public void handleOrder(Order order,String way);
    public List<order> adminHandleOrders(String condition);
    public void deleteUserComment(supportMessage supportMessage, HttpSession httpSession);
    public void addBookPage(Model model);
    public void handleTagsPage(Model model);
    public void handleTags(handleTag handleTag,HttpSession httpSession);
    public void handleHotTag(List<Integer> tagId,HttpSession httpSession);

}
