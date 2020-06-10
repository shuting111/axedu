package com.zb.feign;

import com.zb.pojo.Curriculum;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/10
 * @Version V1.0
 */
@FeignClient(name = "curriculum-server")
public interface CurriculumFeignClient {
    
    @GetMapping("/findCurriculumById/{id}")
    public Curriculum findCurriculumById(@PathVariable("id") Integer id);
}
