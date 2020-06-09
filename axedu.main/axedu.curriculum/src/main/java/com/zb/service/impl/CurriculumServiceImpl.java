package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.zb.config.RabbitConfig;
import com.zb.mapper.CurriculumMapper;
import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;
import com.zb.util.RedisUtil;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
@Service
public class CurriculumServiceImpl implements CurriculumService {
    @Autowired(required = false)
    private CurriculumMapper curriculumMapper;
    @Autowired
    private RedisUtil redisUtils;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Override
    public void CurriculmToRedis() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Curriculum> curriculumList = curriculumMapper.getCurriculumListByMap(map);
            redisUtils.set("curriculum", JSON.toJSONString(curriculumList));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //增加点击量，将请求压入mq
    @Override
    public void IncreaseHits(Integer id) {
        Map<String,Object> param = new HashMap<>();
        param.put("curriculumId",id);
        amqpTemplate.convertAndSend(RabbitConfig.EXCHANGE_TOPIC_INFORM,"inform.curriculum",param);
    }
    //监听  增加点击量发起的队列
    @RabbitListener(queues = RabbitConfig.QUEUE_room)
    public void reciveHits(Map<String,Object> param, Message message, Channel channel){
        if(param.size()>0){
            int id =(Integer) param.get("curriculumId");
            String curriculum = redisUtils.get("curriculum").toString();
            List<Curriculum> list = JSON.parseArray(curriculum,Curriculum.class);
            for (Curriculum c : list) {
                System.out.println(c.getId());
                if(c.getId() == id){
                    c.setHits(c.getHits()+1);
                    System.out.println(c.getHits());
                    break;
                }
            }
            redisUtils.set("curriculum", JSON.toJSONString(list));
        }
    }
    @Scheduled(cron = "00 00 00 * * ?")
    public void guessLike(){
        String curriculum = redisUtils.get("curriculum").toString();
        List<Curriculum> list = JSON.parseArray(curriculum,Curriculum.class);
    }
}
