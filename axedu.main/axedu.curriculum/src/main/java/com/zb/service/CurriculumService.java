package com.zb.service;

import com.zb.pojo.Curriculum;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
public interface CurriculumService {
    public void CurriculmToRedis();

    public void IncreaseHits(Integer id);




}
