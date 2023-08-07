package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemDao {
    public void add(CheckItem checkItem);

    public Page<CheckItem> selectByCondition(String queryString);

    public long findCountByCheckItemId(Integer checkItemId);

    void deleteById(Integer id);

    CheckItem findById(Integer id);

    void editById(CheckItem checkItem);

    List<CheckItem> findAll();
}
