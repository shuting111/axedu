package com.zb.service.impl;

import com.zb.config.RabbitOrderConfig;
import com.zb.mapper.OrdersMapper;
import com.zb.pojo.Orders;
import com.zb.service.CurriculumService;
import com.zb.service.OrderService;
import com.rabbitmq.client.Channel;
import com.zb.service.OrderTempService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private OrderTempService orderTempService;
    @Autowired(required = false)
    private OrdersMapper ordersMapper;


    @Override
    public Integer insertOrders(Orders orders) {
        try {
            return ordersMapper.insertOrders(orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    //分布式锁
    //高并发下的 超时问题 time out error
    //将请求压入mq中，每一个请求立即返回结果
    @Override
    public String qgCurriculum(Integer subjectId, String token) {
        Map<String,Object> param = new HashMap<>();
        param.put("subjectId",subjectId);
        param.put("token",token);
        amqpTemplate.convertAndSend(RabbitOrderConfig.EXCHANGE_ORDER_INFORM,"inform.qg",param);
        return "正在排队中，请等待...";
    }

    @RabbitListener(queues = RabbitOrderConfig.QUEUE_QG)
    public void reviceMq(Map<String,Object>param, Message message, Channel channel){
        String token = (String) param.get("token");
        Integer subjectId =(Integer) param.get("subjectId");
        System.out.println(subjectId+"\t"+token);
        int i = orderTempService.checkStore(subjectId);
        if(i<0){
            System.out.println("该课程名额已满");
            return;
        }
    }


}
