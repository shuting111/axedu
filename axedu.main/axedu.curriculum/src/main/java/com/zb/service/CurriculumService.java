package com.zb.service;

import com.zb.form.CurriArgs;
import com.zb.pojo.Advert;
import com.zb.pojo.Curriculum;
import com.zb.util.PageUtil;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
public interface CurriculumService {
    //将课程全部存入redis
    public void CurriculmToRedis();
    //根据id修改redis里的点击量
    public void IncreaseHits(Integer id);
    //查询猜你喜欢商品
    public List<Curriculum> findLike();
    //维一查询
    public Curriculum findCurriculumById(Integer id);
    //查询精品课程
    public List<Curriculum> findIsChoiceness(Integer isChoiceness);
    //查询全部课程
    public List<Curriculum> findCurricilumAll();
    //es查询
    public PageUtil<Curriculum> findKeyWord(CurriArgs c)throws Exception;

    //添加课程
    public int insertCurriculum(Curriculum curriculum);




}
