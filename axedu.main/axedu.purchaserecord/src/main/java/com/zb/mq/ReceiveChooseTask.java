package com.zb.mq;


import com.rabbitmq.client.Channel;
import com.zb.config.RabbitTaskConfig;
import com.zb.pojo.Task;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReceiveChooseTask {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @RabbitListener(queues = RabbitTaskConfig.XC_LEARNING_ADDCHOOSECOURSE)
    public void receiveTask(Task tasks, Message message, Channel channel){
        System.out.println(tasks.getMyExchange()+"\t"+tasks.getMyRoutingKey());
    }
}
