package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @Author
 * @create 2023/8/31 14:55
 * @Version 1.0
 * @Description:
 */
public interface UserDao {
    public User findByUsername(String username);
}
