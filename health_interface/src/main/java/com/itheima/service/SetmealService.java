package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

//服务接口
public interface SetmealService {

    public void add(Setmeal setmeal, Integer[] checkgroupIds);


    PageResult findPageCondition(QueryPageBean queryPageBean);
}
