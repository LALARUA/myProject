package com.dx.Booker.service;

import com.dx.Booker.generator.extendPojo.commentAndSupport;
import com.dx.Booker.generator.extendPojo.supportMessage;
import com.dx.Booker.generator.mapper.CommentMapper;
import com.dx.Booker.generator.mapper.MessageMapper;
import com.dx.Booker.generator.mapper.SupportMapper;
import com.dx.Booker.generator.mapper.UserMapper;
import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.Message;
import com.dx.Booker.generator.po.Support;
import com.dx.Booker.generator.po.User;
import com.dx.Booker.serviceinterface.CommentSevice;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @description 关于评论的操作
 * @author xiangXX
 * @date 2018/6/03 0012 10:09
  *
 *
 */
@Service
public class CommentServiceImp implements CommentSevice {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private SupportMapper supportMapper;
    @Autowired
    private MessageMapper messageMapper;


    @Override
    /**
     * @description 添加评论
     * @author xiangXX
     * @date 2018/6/03 0012 10:09
      * @param comment 评论信息
     *
     */
    public int addComment(Comment comment) {
        Date date = new Date();

        comment.setDate(date);
        return commentMapper.insert(comment);

    }

    @Override
    /**
     * @description 给评论点赞
     * @author xiangXX
     * @date 2018/6/12 0012 10:10
      * @param supportMessage 点赞评论信息
     *
     */
    public void addSupport(supportMessage supportMessage) {

        supportMapper.addSupport(supportMessage);
        Message message = new Message();
        message.setUserId(supportMessage.getCommentUserId());
        message.setUserSend(supportMessage.getUserId());
        Date date = new Date();
        message.setDate(date);
        message.setDetail(supportMessage.getSupportUserName()+"给你在<<"+supportMessage.getBookTitle()+">>下的评论点了赞");
        messageMapper.insert(message);
    }

    @Transactional
    @Override
    /**
     * @description 查出每本书的评论信息，和每条评论的点赞信息
     * @author xiangXX
     * @date 2018/6/12 0012 10:10
      * @param bookId   书本BookId
     * @param userId   正在浏览此书本详情的UserId
     *
     */
    public List<commentAndSupport> selectCommentAndSupport(Integer bookId,Integer userId) {
        HashMap<String, Integer> hs = new HashMap<>();
        hs.put("bookId",bookId);
        hs.put("currentUser",userId);
        List<com.dx.Booker.generator.extendPojo.commentAndSupport> commentAndSupports = commentMapper.commentsOfBook(hs);
        for (commentAndSupport commentAndSupport:commentAndSupports
             ) {commentAndSupport.setUserBrowseId(userId);
            Date date = commentAndSupport.getDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String showDate = simpleDateFormat.format(date);
            commentAndSupport.setShowDate(showDate);
            if (supportMapper.isUserSupport(commentAndSupport)==0)
            commentAndSupport.setIfUserSupport(false);
            else commentAndSupport.setIfUserSupport(true);
            Integer commentId = commentAndSupport.getId();
            commentAndSupport.setCommentSupports(supportMapper.commentSupports(commentId));
            }
    return commentAndSupports;
    }

    public Comment selectCommentByUserIdAndBookId(Comment comment){
        Comment comment1 = commentMapper.userCommentInBook(comment);
        return comment1;

    }

}
