package com.dx.Booker.service;

import com.dx.Booker.generator.mapper.MessageMapper;
import com.dx.Booker.serviceinterface.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description 处理信息
 * @author xiangXX
 * @date 2018/6/12 0012 10:39
  *
 *
 */
@Service
public class MessageServiceImp implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Override
    /**
     * @description 删除信息
     * @author xiangXX
     * @date 2018/6/12 0012 10:40
      * @param messageId
     *
     */
    public void deleteMessage(Integer messageId) {
        messageMapper.deleteByPrimaryKey(messageId);
    }
}
