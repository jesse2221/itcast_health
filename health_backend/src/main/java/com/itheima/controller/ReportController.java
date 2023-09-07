package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
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
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

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
        try {
            List<Map<String,Object>> setmealCount  = setmealService.findSetmealCount();
            Map SetmealReportMap = new HashMap();
            SetmealReportMap.put("setmealCount",setmealCount);
            List setmealNames = new ArrayList();
            for (Map<String, Object> map : setmealCount) {
                String name = (String) map.get("name");
                setmealNames.add(name);
            }
            SetmealReportMap.put("setmealNames",setmealNames);
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,SetmealReportMap);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> map = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }

}


