package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

//服务接口
public interface SetmealService {

    public void add(Setmeal setmeal, Integer[] checkgroupIds);


    PageResult findPageCondition(QueryPageBean queryPageBean);

    List<Setmeal> getAllSetmeal();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();
}
