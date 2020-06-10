package com.zb.controller;

import com.zb.service.OrderTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class OrderController {
    @Autowired
    private OrderTempService orderTempService;
    @GetMapping("/getTempCount/{id}")
    public int getTempCount(@PathVariable("id") Integer id){
        return orderTempService.getTempCount(id);
    }

}
