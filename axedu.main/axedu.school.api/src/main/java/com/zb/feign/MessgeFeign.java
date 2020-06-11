package com.zb.feign;

import com.zb.dto.Page;
import com.zb.pojo.Message;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "school-server")
public interface MessgeFeign {
    @PostMapping(value = "/MessagePage")
    public Page<Message> MessagePage(@RequestParam Map<String,Object> param);

    @GetMapping(value = "/findById/{id}")
    public Message findById(@PathVariable("id") Integer id);

}
