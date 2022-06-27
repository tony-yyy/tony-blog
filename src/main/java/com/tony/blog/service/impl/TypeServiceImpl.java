package com.tony.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tony.blog.pojo.Type;
import com.tony.blog.mapper.TypeMapper;
import com.tony.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Autowired
    TypeMapper typeMapper;

    @Override
    public Page<Type> getTypesByPageNum(Integer pageNum, Integer pageSize) {
        Page<Type> page = new Page<>(pageNum, pageSize);
        Page<Type> typePage = typeMapper.selectPage(page, null);
        return typePage;
    }

    @Override
    public List<Type> getAll() {
        return typeMapper.selectList(null);
    }
}
