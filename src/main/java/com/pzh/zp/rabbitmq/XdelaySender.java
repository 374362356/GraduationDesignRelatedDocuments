package com.pzh.zp.rabbitmq;

import com.pzh.zp.DTO.NewsDto;
import com.pzh.zp.dao.NewsDao;
import com.pzh.zp.entity.News;
import com.pzh.zp.rabbitmq.config.XdelayConfig;
import com.pzh.zp.service.NewsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class XdelaySender {

        @Autowired
        private RabbitTemplate rabbitTemplate;

        public void send(NewsDto msg, int delayTime) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            this.rabbitTemplate.convertAndSend(XdelayConfig.DELAYED_EXCHANGE_XDELAY, XdelayConfig.DELAY_ROUTING_KEY_XDELAY, msg, message -> {
                message.getMessageProperties().setDelay(delayTime);
                System.out.println(sdf.format(new Date()) + " Delay sent.");
                return message;
            });
        }
    }
