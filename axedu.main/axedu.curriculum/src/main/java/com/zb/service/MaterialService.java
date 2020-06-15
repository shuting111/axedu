package com.zb.service;

import com.zb.pojo.Material;
import com.zb.util.PageUtil;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/12
 * @Version V1.0
 */
public interface MaterialService {
    public PageUtil<Material> findMaterialPage(Integer index,Integer size,Integer gradeId,Integer subjectId);

    public Material findMaterialById(Long id);
}
