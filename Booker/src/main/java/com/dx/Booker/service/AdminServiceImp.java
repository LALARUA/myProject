package com.dx.Booker.service;

import com.dx.Booker.generator.extendPojo.*;
import com.dx.Booker.generator.mapper.*;
import com.dx.Booker.generator.po.*;
import com.dx.Booker.serviceinterface.AdminService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * @description 管理员操作Service
 * @author xiangXX
 * @date 2018/6/1 0012 9:52
 */
@Service
public class AdminServiceImp implements AdminService {
    @Autowired
    private BooksMapper booksMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private LabelAndTagMapper labelAndTagMapper;

    @Override
    /**
     * @description 添加Book操作
     * @author xiangXX
     * @date 2018/6/1 0012 9:53
      * @param addBook 要添加的book信息
     * @param image book图片信息
     *
     */
    public void addBook(addBook addBook, MultipartFile image) {
        //存储图片的物理地址
        String pic_path = "/opt/tomcat/bookImage/";
        //原始名称
        String originalFilename = image.getOriginalFilename();
        //新的文件名
        String newfilename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //新图片
        File file = new File(pic_path + newfilename);
        //将内存中的图片写入磁盘
        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
            e.printStackTrace();
        }
        //将新图片的名称写入book中
        addBook.setImagepath("/bookImage/" + newfilename);
        List<String> bookTags = addBook.getBookTags();
        String tags = "";
        for (String tag : bookTags
                ) {
            tags = tags + "/" + tag;
        }
        addBook.setTags(tags);
        Date date = new Date();
        addBook.setPutaway(date);
        booksMapper.addBook(addBook);
    }

    @Override
    /**
     * @description 删除Book
     * @author xiangXX
     * @date 2018/6/1 0012 9:54
      * @param bookId book要删除的BookId
     *
     */
    public void deleteBook(Integer bookId) {
        booksMapper.deleteByPrimaryKey(bookId);
    }

    @Override
    /**
     * @description 更新BooK非图片信息
     * @author xiangXX
     * @date 2018/6/1 0012 9:55
      * @param books 更新的book信息
     *
     */
    public void updateBook(Books books) {

        booksMapper.updateBook(books);
    }

    @Override
    /**
     * @description 更新book的图片
     * @author xiangXX
     * @date 2018/6/1 0012 9:55
      * @param bookDetail book的详细信息，用于回显数据
     * @param image 新的图片
     *
     */
    public void updateBookImage(Books bookDetail, MultipartFile image) {
        //存储图片的物理地址
        String pic_path = "/opt/tomcat";

        String imagepath = pic_path + bookDetail.getImagepath();

        File oldImageFile = new File(imagepath);
        if (oldImageFile.exists())
            oldImageFile.delete();
        //原始名称
        String originalFilename = image.getOriginalFilename();
        //新的文件名
        String newfilename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //新图片
        File file = new File(pic_path + "/bookImage/" + newfilename);
        //将内存中的图片写入磁盘
        try {
            image.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //将新图片的名称写入book中
        bookDetail.setImagepath("/bookImage/" + newfilename);
        booksMapper.updateImagePath(bookDetail);
    }

    @Transactional
    @Override
    /**
     * @description 管理员处理订单
     * @author xiangXX
     * @date 2018/6/1 0012 9:56
      * @param order 要处理的订单信息
     * @param way 如何处理订单
     *
     */
    public void handleOrder(Order order, String way) {
        Message message = new Message();
        message.setDate(new Date());
        message.setUserSend(order.getAdminId());
        message.setUserId(order.getUserId());
        if (way.equals("发货")) {
            order.setCondition("已发货");
            message.setDetail("你订单号为" + order.getId() + "的订单已经打包出发了，请注意查收哦!");
        } else if (way.equals("同意退款")) {
            order.setCondition("退款完成");
            message.setDetail("你订单号为" + order.getId() + "的订单已经同意退款，注意查收退款信息哦");
        } else if (way.equals("拒绝退款")) {
            order.setCondition("支付成功");
            message.setDetail("很抱歉，你订单号为" + order.getId() + "的订单无法退款，随后将为你发货，注意查收");
        } else if (way.equals("同意退货")) {
            order.setCondition("同意退货");
            message.setDetail("你订单号为" + order.getId() + "的订单的退货申请已经通过，请尽快寄件到XXX");
        } else if (way.equals("拒绝退货")) {
            order.setCondition("订单完成");
            message.setDetail("很抱歉，你订单号为" + order.getId() + "的订单的退货申请没有通过,已经强制收货 嘿嘿");
        } else if (way.equals("确认收货")) {
            order.setCondition("退货完成");
            message.setDetail("你订单号为" + order.getId() + "的订单的退货已完成,请注意查收退款");
        }

        messageMapper.insert(message);
        orderMapper.handleOrder(order);
    }

    @Override
    /**
     * @description 得到要处理的订单信息
     * @author xiangXX
     * @date 2018/6/1 0012 9:58
      * @param condition 想要获得哪种订单信息
     *
     */
    public List<order> adminHandleOrders(String condition) {
        List<order> orders = orderMapper.ordersByCondition(condition);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (order order : orders
                ) {
            Date createtime = order.getCreatetime();
            order.setDatetime(simpleDateFormat.format(createtime));

        }
        return orders;
    }

    @Override
    /**
     * @description 删除用户评论
     * @author xiangXX
     * @date 2018/6/12 0012 10:49
      * @param comment
     *
     */
    public void deleteUserComment(supportMessage supportMessage, HttpSession httpSession) {
        User user=(User)httpSession.getAttribute("user");
        commentMapper.deleteByPrimaryKey(supportMessage.getCommentId());
        Message message = new Message();
        message.setUserId(supportMessage.getCommentUserId());
        message.setUserSend(user.getId());
        message.setDate(new Date());
        message.setDetail("你在《"+supportMessage.getBookTitle()+"》下的评论因言语不当已被删除，今后请注意言行");
        messageMapper.insert(message);
    }

    /**
     * @description 来到添加Book的页面
     * @author xiangXX
     * @date 2018/6/20 0020 8:47
      * @param null
     *
     */
    @Override
    public void addBookPage(Model model) {
        List<tag> tags = labelAndTagMapper.selectTags();
        model.addAttribute("tags",tags);

    }

    /**
     * @description 来到操作Tag的页面
     * @author xiangXX
     * @date 2018/6/20 0020 8:48
      * @param null
     *
     */
    @Override
    public void handleTagsPage(Model model) {
        List<allTags> allTags = labelAndTagMapper.selectAllTags();
        List<tag> tags=labelAndTagMapper.selectTags();
        model.addAttribute("labels",allTags);
        model.addAttribute("tags",tags);
    }

    @Override
    public void handleTags(handleTag handleTag,HttpSession httpSession) {
        Integer flag = handleTag.getFlag();

//        在已有label上添加tag
        if (flag==0){
            labelAndTagMapper.insertNewTag(handleTag);

        }
//        同时添加label和tag
        else if (flag==1)
        {
            labelAndTagMapper.insertNewLabel(handleTag);
            labelAndTagMapper.insertNewTag(handleTag);

        }

//        删除label
        else if (flag==2){
            labelAndTagMapper.deleteLabel(handleTag);

        }

//        删除tag
        else if (flag==3){
            labelAndTagMapper.deleteTag(handleTag);

        }

//        更新label
        else if (flag==4){
            labelAndTagMapper.updateLabel(handleTag);

        }

//        更新tag
        else if (flag==5){
            labelAndTagMapper.updateTag(handleTag);

        }
        httpSession.setAttribute("labels",labelAndTagMapper.selectAllTags());
        httpSession.setAttribute("hotTags",labelAndTagMapper.selectHotTags(1));
    }
@Transactional
    @Override
    public void handleHotTag(List<Integer> tagId,HttpSession httpSession) {
        labelAndTagMapper.setStatus(0);
        labelAndTagMapper.updateHotTag(tagId);
    httpSession.setAttribute("labels",labelAndTagMapper.selectAllTags());
    httpSession.setAttribute("hotTags",labelAndTagMapper.selectHotTags(1));

    }


}
