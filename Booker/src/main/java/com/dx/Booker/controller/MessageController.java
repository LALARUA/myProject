package com.dx.Booker.controller;

import com.dx.Booker.generator.mapper.MessageMapper;
import com.dx.Booker.serviceinterface.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/message")
@Controller
public class MessageController {
   @Autowired
   private MessageService messageService;
    @ResponseBody
    @RequestMapping("/deleteMessage")
    /**
     * @description 删除消息
     * @author xiangXX
     * @date 2018/6/15 0015 13:03
      * @param messageId 消息的Id
     *
     */
    public Map deleteMessgge(Integer messageId){

        HashMap<Object, Object> data = new HashMap<>();
        String info = null;
        try {
            messageService.deleteMessage(messageId);
            info = "删除成功";
        } catch (Exception e) {
            info = "删除失败";
            e.printStackTrace();
        }
        data.put("info",info);
        return data;
    }
}
