package com.tony.blog.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.blog.pojo.Type;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
public interface TypeService extends IService<Type> {

    Page<Type> getTypesByPageNum(Integer pageNum, Integer pageSize);

    List<Type> getAll();
}
