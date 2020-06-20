package com.zb.service;

import com.zb.pojo.Taskhistory;
import org.apache.ibatis.annotations.Param;

public interface TaskhistoryService {
    public Integer insertTaskhistory(Taskhistory taskhistory);

    public Taskhistory getTaskhistoryById(@Param(value = "id") Long id);

    public Integer updateTaskhistory(Taskhistory taskhistory);
}
