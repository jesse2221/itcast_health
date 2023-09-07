package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    // @Autowired
    // private MemberDao memberDao;
    public Map<String, Object> getBusinessReportData() {
        Map<String,Object> map = new HashMap<String, Object>();
        // String todayTime = DateUtils.parseDate2String(DateUtils.getToday());
        // System.out.println(todayTime);

        /**
         *  reportDate:null,
*                     todayNewMember :0,
*                     totalMember :0,
*                     thisWeekNewMember :0,
*                     thisMonthNewMember :0,
*                     todayOrderNumber :0,
*                     todayVisitsNumber :0,
*                     thisWeekOrderNumber :0,
*                     thisWeekVisitsNumber :0,
*                     thisMonthOrderNumber :0,
*                     thisMonthVisitsNumber :0,
         */
        // map.put("reportDate",todayTime);
        map.put("reportDate","20230907 ");
        return map;
    }
}
