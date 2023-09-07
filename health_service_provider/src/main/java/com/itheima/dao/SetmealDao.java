package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealDao {
    public void add(Setmeal setmeal);
    public void setSetmealAndCheckGroup(Map<String, Integer> map);

    Page<Setmeal> findPageCondition(String queryString);

    List<Setmeal> getAllSetmeal();

    Setmeal findById(int id);

    List<Map<String, Object>> findSetmealCount();
}
