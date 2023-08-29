package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.entity.Result;

import java.util.Map;

/**
 * @Author
 * @create 2023/8/25 16:09
 * @Version 1.0
 * @Description:
 */
public interface OrderService {
    public Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
