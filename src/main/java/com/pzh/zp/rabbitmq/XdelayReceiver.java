package com.pzh.zp.rabbitmq;

import com.pzh.zp.DTO.NewsDto;
import com.pzh.zp.service.NewsService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@EnableRabbit
@Configuration
public class XdelayReceiver {
    @Resource
    private NewsService newsService;

    @RabbitListener(queues = com.pzh.zp.rabbitmq.config.XdelayConfig.IMMEDIATE_QUEUE_XDELAY)
    public void get(NewsDto msg) {
        try {
            newsService.InsertTiming(msg);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(("收到延时消息时间：" + sdf.format(new Date()) + " Delay sent."));*/
        System.out.println(("收到延时消息:" + msg.toString()));
    }
}
