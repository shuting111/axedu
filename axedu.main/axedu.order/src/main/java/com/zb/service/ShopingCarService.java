package com.zb.service;

import com.zb.pojo.Cart;

public interface ShopingCarService {
    /**
     * 添加购物车
     * @param uid
     * @param subjectId
     * @param num
     * @return
     */
    public boolean addCart(Integer uid, Integer subjectId, Integer num);

    /**
     * 修改数量
     * @param uid
     * @param subjectId
     * @param num
     * @param op
     */
    public void updateNum(Integer uid, Integer subjectId, Integer num, String op);

    /**
     * 删除购物车的选项
     * @param uid
     * @param subjectId
     */
    public void delItem(Integer uid, Integer subjectId);

    /**
     * 获取购物车信息
     * @param uid
     * @return
     */
    public Cart getCartAllItem(Integer uid);
}
