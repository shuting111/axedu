package com.zb.mq;


import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.zb.config.RabbitTaskConfig;
import com.zb.pojo.Purchaserecord;
import com.zb.pojo.Task;
import com.zb.pojo.Taskhistory;
import com.zb.service.PurchaserecordService;
import com.zb.service.TaskhistoryService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReceiveChooseTask {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private PurchaserecordService purchaserecordService;
    @Autowired
    private TaskhistoryService taskhistoryService;
    @RabbitListener(queues = RabbitTaskConfig.XC_LEARNING_ADDCHOOSECOURSE)
    public void receiveTask(Task tasks, Message message, Channel channel){
        System.out.println(tasks.getMyExchange()+"\t"+tasks.getMyRoutingKey());
        Purchaserecord purchaserecord = JSON.parseObject(tasks.getRequestBody(), Purchaserecord.class);
        Map<String,Object>param = new HashMap<>();
        param.put("goodsId",purchaserecord.getGoodsId());
        param.put("userId",purchaserecord.getUserId());
        Purchaserecord map = purchaserecordService.findByMap(param);
        if(map==null){
            Integer integer = purchaserecordService.insertPurchaserecord(purchaserecord);
        }else{
            if(map.getStatus()==1){
                map.setStatus(0);
                purchaserecordService.updatePurchaserecord(map);
            }else{
                System.out.println("不可重复购买");
            }
        }
        Taskhistory taskhistory = taskhistoryService.getTaskhistoryById(Long.parseLong(tasks.getTaskId()));
        if(taskhistory==null){
            taskhistory = new Taskhistory();
            taskhistory.setStatus(tasks.getStatus());
            taskhistory.setCreateTime(tasks.getCreateTime());
            taskhistory.setMyExchange(tasks.getMyExchange());
            taskhistory.setMyRoutingKey(tasks.getMyRoutingKey());
            taskhistory.setRequestBody(tasks.getRequestBody());
            taskhistory.setTaskId(tasks.getTaskId());
            taskhistory.setVersion(tasks.getVersion());
            Integer integer = taskhistoryService.insertTaskhistory(taskhistory);
        }else{
            taskhistory.setStatus(2);
            taskhistoryService.updateTaskhistory(taskhistory);
        }
        amqpTemplate.convertAndSend(RabbitTaskConfig.EX_LEARNING_ADDCHOOSECOURSE,RabbitTaskConfig.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY,tasks);

    }
}
