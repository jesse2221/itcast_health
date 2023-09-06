package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.utils.DateUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author
 * @create 2023/9/5 10:52
 * @Version 1.0
 * @Description:
 */
@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,Object> map = new HashMap<String, Object>();
        List months = new ArrayList();
        Calendar calendar= Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            Date date = calendar.getTime();
            months.add(new SimpleDateFormat("yyyy.MM").format(date));
        }
        map.put("months",months);

        List<Integer> memberCount= null;
        try {
            memberCount = memberService.findMemberCountByMonth(months);
            map.put("memberCount",memberCount);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        Map map = new HashMap();
        List setmealNames=new ArrayList();
        setmealNames.add("套餐1");
        setmealNames.add("套餐2");
        setmealNames.add("套餐3");
        map.put("setmealNames",setmealNames);

        List<Map> setmealCount = new ArrayList();
        Map map1=new HashMap();
        map1.put("name","套餐1");
        map1.put("value",10);
        setmealCount.add(map1);
        Map map2=new HashMap();
        map2.put("name","套餐2");
        map2.put("value",32);
        setmealCount.add(map2);
        Map map3=new HashMap();
        map3.put("name","套餐3");
        map3.put("value",32);
        setmealCount.add(map3);
        map.put("setmealCount",setmealCount);
        // setmealCount.add()
        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
    }

}


