package com.tony.blog.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tony.blog.pojo.ResultInfo;
import com.tony.blog.pojo.Type;
import com.tony.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/")
public class TypeController {
    @Autowired
    TypeService typeService;

    @RequestMapping({"/admin/types", "/types"})
    public Page<Type> types(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum){
        Page<Type> page = typeService.getTypesByPageNum(pageNum, 10);
        return page;
    }

    @RequestMapping({"/admin/types/all", "/types/all"})
    public List<Type> typesAll(){
        List<Type> typeList = typeService.getAll();
        return typeList;
    }

    /**
     * 添加
     * @param type
     * @return
     */
    @PostMapping("/admin/type/addNewType")
    public ResultInfo addNewType(@RequestBody Type type){
        System.out.println(type);
        boolean save = typeService.save(type);
        return new ResultInfo(!save, save?"":"添加失败!");
    }

    /**
     * 更新
     * @param
     * @return
     */
    @PostMapping("/admin/type/addNewType/update")
    public ResultInfo updateType(@RequestBody Type type){
        System.out.println(type);
        boolean save = typeService.updateById(type);
        return new ResultInfo(!save, save?"":"更新失败!");
    }

    @RequestMapping("/admin/type/delete/{id}")
    public ResultInfo deleteType(@PathVariable("id") Integer id){
        boolean remove = typeService.removeById(id);
        return new ResultInfo(!remove, remove?"":"删除失败!");
    }

}

