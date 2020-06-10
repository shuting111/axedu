package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import com.zb.config.RabbitConfig;
import com.zb.mapper.CurriculumMapper;
import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;
import com.zb.util.RedisUtil;
import com.zb.util.SortList;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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

    @Override
    public List<Curriculum> findLike() {
        Map<String,Object> param = new HashMap<>();
        param.put("like",1);
        List<Curriculum> list = null;
        String key = "likeCurriculum";
        if(redisUtils.hasKey(key)){
            System.out.println("查询redis");
            String jsonobj = redisUtils.get(key).toString();
            list = JSON.parseArray(jsonobj, Curriculum.class);
        }else {
            System.out.println("从数据库中查询");
            try {
                list = curriculumMapper.getCurriculumListByMap(param);
                redisUtils.set(key,JSON.toJSONString(list),24*60*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    @Cacheable(value = "cache",key = "'curr'+#id")
    public Curriculum findCurriculumById(Integer id) {
        System.out.println("encache中没有数据");
        Curriculum curriculum = null;
        String key = "curr"+id;
        if(redisUtils.hasKey(key)){
            System.out.println("查询redis");
            String jsonstr = redisUtils.get(key).toString();
            curriculum = JSON.parseObject(jsonstr,Curriculum.class);
        }else {
            try {
                System.out.println("从数据库中查询");
                curriculum = curriculumMapper.getCurriculumById(Long.parseLong(id + ""));
                redisUtils.set(key,JSON.toJSONString(curriculum),24*60*60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return curriculum;
    }

    @Override
    public List<Curriculum> findIsChoiceness(Integer isChoiceness) {
        Map<String,Object> param = new HashMap<>();
        param.put("isChoiceness",isChoiceness);
        List<Curriculum> list = null;
        try {
            list=curriculumMapper.getCurriculumListByMap(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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

    @Scheduled(cron = "00 21 22 * * ?")
    public void guessLike(){
        String curriculum = redisUtils.get("curriculum").toString();
        List<Curriculum> list = JSON.parseArray(curriculum,Curriculum.class);
        SortList.sort(list, "hits", SortList.DESC);
        for (int i = 0;i<list.size();i++) {
            Curriculum c = list.get(i);
            if(i<5){
                c.setLike(1);
            }else {
                c.setLike(2);
            }
            try {
                curriculumMapper.updateCurriculum(c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
