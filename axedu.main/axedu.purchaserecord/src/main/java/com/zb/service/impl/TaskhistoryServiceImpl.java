package com.zb.service.impl;

import com.zb.mapper.TaskhistoryMapper;
import com.zb.pojo.Taskhistory;
import com.zb.service.TaskhistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskhistoryServiceImpl implements TaskhistoryService {
    @Autowired(required = false)
    private TaskhistoryMapper taskhistoryMapper;
    @Override
    public Integer insertTaskhistory(Taskhistory taskhistory) {
        try {
            return taskhistoryMapper.insertTaskhistory(taskhistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Taskhistory getTaskhistoryById(Long id) {
        try {
            return taskhistoryMapper.getTaskhistoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer updateTaskhistory(Taskhistory taskhistory) {
        try {
            return taskhistoryMapper.updateTaskhistory(taskhistory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
