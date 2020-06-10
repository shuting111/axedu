package com.zb.service.impl;

import com.zb.mapper.GradeMapper;
import com.zb.pojo.Grade;
import com.zb.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/10
 * @Version V1.0
 */
@Service
public class GradeServiceImpl implements GradeService {
    @Autowired(required = false)
    private GradeMapper gradeMapper;



}
