/*
package com.tony.blog.utils;

import com.tony.blog.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;


import java.util.Random;

public class MailTask {
    @Autowired
    private JavaMailSender mailSender;

    public void getMailCode(Comment){
        //邮件设置1：一个简单的邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("来自【Tony blog】的回复消息");
        message.setText("");
        message.setTo(mail);
        message.setFrom("1605337475@qq.com");
        this.mailSender.send(message);
    }
}
*/
