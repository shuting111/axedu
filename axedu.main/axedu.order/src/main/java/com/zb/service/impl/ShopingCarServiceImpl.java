package com.zb.service.impl;

import com.zb.pojo.Cart;
import com.zb.pojo.Shoping;
import com.zb.service.ShopingCarService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShopingCarServiceImpl implements ShopingCarService {
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean addCart(Integer uid, Integer subjectId, Integer num) {
        Map<String ,Object> data = new HashMap<>();
        data.put(subjectId+"",num);
        boolean hmset = redisUtil.hmset("subject:" + uid, data);
        return  hmset;
    }

    @Override
    public void updateNum(Integer uid, Integer subjectId, Integer num, String op) {
        if(op.equals("add")){
            redisUtil.hincr("subject:"+uid,subjectId+"",num);
        }else{
            redisUtil.hdecr("subject:"+uid,subjectId+"",num);
        }
    }

    @Override
    public void delItem(Integer uid, Integer subjectId) {
        redisUtil.hdel("subject:"+uid,subjectId+"");
    }

    @Override
    public Cart getCartAllItem(Integer uid) {
        Cart cart = new Cart();
        List<Shoping>list  =new ArrayList<>();
        Map<Object, Object> all = redisUtil.getAll("subject:" + uid);
        Iterator<Object> it =all.keySet().iterator();
        while (it.hasNext()){
            Object key = it.next();//商品编号
            Object val = all.get(key);//商品数量
            System.out.println(key+"\t"+val);
            Shoping shoping = new Shoping();
            shoping.setShopingId(Integer.parseInt(key.toString()));
            shoping.setNumber(Integer.parseInt(val.toString()));
            String shopingKey = "shoping:"+shoping.getShopingId();
            Map<Object, Object> hmget = redisUtil.hmget(shopingKey);
            shoping.setShopingName(hmget.get("subjectName").toString());
            list.add(shoping);
        }
        cart.setItems(list);
        return cart;
    }
}
