package com.dx.Booker.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;

import javax.mail.SendFailedException;
/**
 * @description 发送邮箱
 * @author xiangXX
 * @date 2018/6/17 0017 10:08
  * @param null
 *
 */
@Component
public class MailUtil {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendSimpleEmail(String deliver, String[] receiver, String[] carbonCopy, String subject, String content) {
        long startTimestamp = System.currentTimeMillis();
        logger.info("Start send mail ... ");
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(deliver);
            message.setTo(receiver);
            message.setCc(carbonCopy);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            logger.info("Send mail success, cost {} million seconds", System.currentTimeMillis() - startTimestamp);
        } catch (MailException e) {
            e.printStackTrace();
            throw e;
        }
    }

}
