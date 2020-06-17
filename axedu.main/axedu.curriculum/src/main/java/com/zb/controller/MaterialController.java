package com.zb.controller;

import com.zb.pojo.Material;
import com.zb.service.MaterialService;
import com.zb.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/13
 * @Version V1.0
 */
@RestController
public class MaterialController {
    @Autowired
    private MaterialService materialService;
    //根据年级id和科目id分页查询资料
    @GetMapping("/findMaterialPage")
    public PageUtil<Material> findMaterialPage(Integer index, Integer size, Integer gradeid, Integer subjectid){
        return materialService.findMaterialPage(index,size,subjectid,gradeid);
    }
    //唯一查询资料信息
    @GetMapping("/findMaterialById")
    public Material findMaterialById(Long id){
        return materialService.findMaterialById(id);
    }
}
