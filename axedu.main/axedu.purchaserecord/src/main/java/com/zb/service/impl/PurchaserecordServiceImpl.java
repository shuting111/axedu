package com.zb.service.impl;

import com.sun.org.apache.regexp.internal.RE;
import com.zb.mapper.PurchaserecordMapper;
import com.zb.pojo.Purchaserecord;
import com.zb.service.PurchaserecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PurchaserecordServiceImpl implements PurchaserecordService {

    @Autowired(required = false)
    private PurchaserecordMapper purchaserecordMapper;

    @Scheduled(cron =  "0 0 23 * * ?")
    @Override
    public Integer updatePurchaserecordStatus() {
        return purchaserecordMapper.updatePurchaserecordStatus();
    }


    @Override
    public Integer insertPurchaserecord(Purchaserecord purchaserecord) {
        try {
            return purchaserecordMapper.insertPurchaserecord(purchaserecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Integer updatePurchaserecord(Purchaserecord purchaserecord) {
        try {
            return purchaserecordMapper.updatePurchaserecord(purchaserecord);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Purchaserecord findByMap(Map<String, Object> param) {
        return purchaserecordMapper.findByMap(param);
    }
}
