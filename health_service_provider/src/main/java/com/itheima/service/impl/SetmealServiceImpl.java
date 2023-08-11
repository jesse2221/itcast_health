package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.constant.RedisConstant;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;
import com.itheima.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 检查项服务
 */
@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Autowired
    private JedisPool jedisPool;

    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        //将图片名称保存到Redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES,setmeal.getImg());
        if(checkgroupIds!=null && checkgroupIds.length>0){
            for (Integer checkgroupId : checkgroupIds) {
                Map map=new HashMap();
                map.put("setmeal_id",setmeal.getId());
                map.put("checkgroup_id",checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
    }

    public PageResult findPageCondition(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> pageList=setmealDao.findPageCondition(queryString);
        return new PageResult(pageList.getTotal(),pageList.getResult());
    }

    public List<Setmeal> getAllSetmeal() {
        return setmealDao.getAllSetmeal();
    }

    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }
}
