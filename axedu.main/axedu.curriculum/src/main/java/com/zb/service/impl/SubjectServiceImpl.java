package com.zb.service.impl;

import com.alibaba.fastjson.JSON;
import com.zb.mapper.SubjectMapper;
import com.zb.pojo.Subject;
import com.zb.service.SubjectService;
import com.zb.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/16
 * @Version V1.0
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    @Autowired(required = false)
    private SubjectMapper subjectMapper;
    @Autowired(required = false)
    private RedisUtil redisUtil;


}
