package com.zb.controller;

import com.zb.pojo.Purchaserecord;
import com.zb.service.PurchaserecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PurchaserecordController {
    @Autowired
    private PurchaserecordService purchaserecordService;
    @GetMapping("/getPurchaserecordById/{id}")
    public Purchaserecord getPurchaserecordById(@PathVariable("id") Long id){
        return purchaserecordService.getPurchaserecordById(id);
    }
}
