package com.zb.service;

import com.zb.pojo.Purchaserecord;
import org.apache.ibatis.annotations.Param;

public interface PurchaserecordService {
    public Purchaserecord getPurchaserecordById(Long id);
}
