package com.zb.service;

import com.zb.pojo.Purchaserecord;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface PurchaserecordService {

    public Integer updatePurchaserecordStatus();

    public Integer insertPurchaserecord(Purchaserecord purchaserecord);

    public Integer updatePurchaserecord(Purchaserecord purchaserecord);

    public Purchaserecord findByMap(Map<String,Object> param);
}
