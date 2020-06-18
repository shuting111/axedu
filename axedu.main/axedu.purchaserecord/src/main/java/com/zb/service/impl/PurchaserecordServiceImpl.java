package com.zb.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.zb.mapper.PurchaserecordMapper;
import com.zb.pojo.Purchaserecord;
import com.zb.service.PurchaserecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaserecordServiceImpl implements PurchaserecordService {

    @Autowired(required = false)
    private PurchaserecordMapper purchaserecordMapper;
    @Override
    public Purchaserecord getPurchaserecordById(Long id) {
        try {
            return purchaserecordMapper.getPurchaserecordById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
