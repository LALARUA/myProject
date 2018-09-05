package com.dx.Booker.controller;


import com.dx.Booker.generator.extendPojo.supportMessage;
import com.dx.Booker.generator.mapper.SupportMapper;
import com.dx.Booker.generator.po.Comment;
import com.dx.Booker.generator.po.Support;
import com.dx.Booker.serviceinterface.CommentSevice;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentSevice commentSevice;


    @ResponseBody
    @RequestMapping("/addComment")
    /**
     * @description 给Book添加评论
     * @author xiangXX
     * @date 2018/6/15 0015 12:58
      * @param comment 评论信息
     *
     */
    public Map addComment(Comment comment){
        HashMap<Object, Object> data = new HashMap<>();
        String info=null;

        try {
            commentSevice.addComment(comment);
            info = "发表成功";
        } catch (Exception e) {
            info = "发表失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
    @ResponseBody
    @RequestMapping("/supportComment")
    /**
     * @description 给评论点赞
     * @author xiangXX
     * @date 2018/6/15 0015 12:58
      * @param supportMessage 点赞信息
     *
     */
    public Map supportComment(supportMessage supportMessage){
        HashMap<Object, Object> data = new HashMap<>();
        try {
            commentSevice.addSupport(supportMessage);
        }catch (Exception e){
            data.put("error","点赞失败");
        }
        return data;
    }

    @ResponseBody
    @RequestMapping("/supportReply")
    public String supportReply(Integer userId,Integer replyId){

        commentSevice.supportReply(userId,replyId);
        return null;
    }

}
