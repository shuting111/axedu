package com.zb.service;

public interface OrderTempService  {
    public int getTempCount(Integer id);

    public void CurriculumToRedis();

    /**
     * 查询课程剩余名额
     * @param id
     * @return
     */
    public int checkStore(Integer id);

    /**
     * 添加临时记录
     * @param id
     * @param uid
     * @return
     */
    public int lockRoomStock(Integer id, Integer uid);
}
