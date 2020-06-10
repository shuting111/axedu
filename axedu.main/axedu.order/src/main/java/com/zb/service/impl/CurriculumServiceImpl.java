package com.zb.service.impl;

import com.zb.mapper.CurriculumMapper;
import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurriculumServiceImpl implements CurriculumService {
    @Autowired(required = false)
    private CurriculumMapper curriculumMapper;

    @Override
    public List<Curriculum> findIsDiscount() {
        return curriculumMapper.findIsDiscount();
    }
}
