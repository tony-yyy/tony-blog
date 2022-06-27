package com.tony.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tony.blog.pojo.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tony
 * @since 2022-06-16
 */
public interface UserService extends IService<User> {

    User checkUser(String username, String password);
}
