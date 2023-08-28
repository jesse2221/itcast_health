package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @create 2023/8/28 16:15
 * @Version 1.0
 * @Description:
 */
@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;

    public Result order(Map map) throws Exception {
        //1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if(orderSetting==null){
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if(reservations>=number){
            return new Result(false,MessageConstant.ORDER_FULL);
        }

        //3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        Member member = memberDao.findByTelephone((String) map.get("telephone"));
        if(member!=null){
            Integer memberId = member.getId();
            int setmealId = Integer.parseInt((String) map.get("setmealId"));
            Order order=new Order(memberId,date,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if(list!=null && list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else {
            //4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
            //当前用户不是会员，需要添加到会员表
            member=new Member();
            member.setPhoneNumber((String) map.get("telephone"));
            member.setName((String) map.get("name"));
            member.setIdCard((String) map.get("idCard"));
            member.setSex((String) map.get("sex"));
            member.setRegTime(new Date());
            memberDao.add(member);//自动完成用户注册
        }
        //5、预约成功，更新当日的已预约人数
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.add(order);

        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order.getId());
    }
}
