package com.zb.service;

import com.zb.pojo.Orders;

public interface OrderService {

    public Integer insertOrders(Orders orders);

    public String qgCurriculum(Integer subjectId, String token);
}
