package com.zb.service.impl;

import com.zb.mapper.OrdertempMapper;
import com.zb.service.OrderTempService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderTempServiceImpl implements OrderTempService {

    @Autowired(required = false)
    private OrdertempMapper ordertempMapper;
    @Override
    public int getTempCount(Integer id) {
        return ordertempMapper.getTempCount(id);
    }
}
