package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.mapper.RegisterMapper;
import com.zb.pojo.Register;
import com.zb.service.RegisterService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired(required = false)
    private RegisterMapper registerMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public Register getRegisterByToken(String token) {
        if(redisUtil.hasKey(token)){
            String string = redisUtil.get(token).toString();
            Register register = JSON.parseObject(string, Register.class);
            return register;
        }else{
            return null;
        }
    }
}
