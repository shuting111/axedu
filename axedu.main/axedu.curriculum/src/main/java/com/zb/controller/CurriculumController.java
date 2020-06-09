package com.zb.controller;

import com.zb.pojo.Curriculum;
import com.zb.service.CurriculumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/8
 * @Version V1.0
 */
@RestController
public class CurriculumController {
    @Autowired
    private CurriculumService curriculumService;

    //用户点击课程增加点击量
    @GetMapping("/IncreaseHits/{id}")
    public void IncreaseHits(@PathVariable("id") Integer id){
        curriculumService.IncreaseHits(id);
    }
    //将课程存入redis
    @GetMapping("/CurriculmToRedis")
    public void CurriculmToRedis(){
        curriculumService.CurriculmToRedis();
    }

    //前台显示猜你喜欢
    @GetMapping("/findLike")
    public List<Curriculum> findLike(){
        return curriculumService.findLike();
    }
}
