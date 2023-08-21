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
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @Value("${out_put_path}")
    private String OutPutPath;

    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map map = new HashMap();
                map.put("setmeal_id", setmeal.getId());
                map.put("checkgroup_id", checkgroupId);
                setmealDao.setSetmealAndCheckGroup(map);
            }
        }
        //将图片名称保存到Redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        //当添加套餐后需要重新生成静态页面（套餐列表页面、套餐详情页面）
        generateMobileStaticHtml();

    }

    public void generateMobileStaticHtml() {
        //准备模板里的数据
        List<Setmeal> list = setmealDao.getAllSetmeal();
        generateMobileSetmealListHtml(list);
        generateMobileSetmealDetailHtml(list);
    };

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> list) {
        Map map = new HashMap();
        map.put("setmealList", list);
        generateHtml("mobile_setmeal.ftl", "m_setmeal.html", map);
    }

    //生成套餐详情静态页面（多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> list) {
        for (Setmeal setmeal : list) {
            Map map = new HashMap();
            map.put("setmeal", setmealDao.findById(setmeal.getId()));
            generateHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", map);
        }
    }

    //通用的方法，用于生成静态页面
    public void generateHtml(String templateName, String htmlPageName, Map map) {
        //获得配置对象
        Configuration configuration = freeMarkerConfig.getConfiguration();
        Writer out = null;
        try {
            //加载模板文件
            Template template = configuration.getTemplate(templateName);
            out = new FileWriter(new File(OutPutPath + "/" + htmlPageName));
            template.process(map, out);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PageResult findPageCondition(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> pageList = setmealDao.findPageCondition(queryString);
        return new PageResult(pageList.getTotal(), pageList.getResult());
    }

    public List<Setmeal> getAllSetmeal() {
        return setmealDao.getAllSetmeal();
    }

    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }
}
