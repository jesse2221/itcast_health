package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;

import java.util.List;

//服务接口
public interface CheckGroupService {

    public void add(CheckGroup checkGroup, Integer[] checkitemIds);


    PageResult findPageCondition(QueryPageBean queryPageBean);

    CheckGroup findById(Integer id);

    List<Integer> findCheckItemIdsByCheckGroupId(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);


    List<CheckGroup> findAll();
}
