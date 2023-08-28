package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @Author
 * @create 2023/8/25 15:57
 * @Version 1.0
 * @Description:
 */
@RequestMapping("/order")
@RestController
public class OrderController {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderService orderService;

    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        //从redis中获取保存的验证码
        String codeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        //将用户输入的验证码和redis中保存的验证码进行比对
        Result result=null;
        if(validateCode!=null && codeInRedis!=null && validateCode.equals(codeInRedis)){
            //如果比对成功，调用服务完成业务预约处理
            try {
                map.put("orderType",Order.ORDERTYPE_WEIXIN);//设置预约途径
                result = orderService.order(map);//远程调用有超时风险
            } catch (Exception e) {
                e.printStackTrace();
            }
            //预约成功发送成功短信
           /* try {
                SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone, (String) map.get("orderDate"));
            } catch (ClientException e) {
                e.printStackTrace();
            }*/
            return result;
        }else {
            //如果比对失败，返回结果给页面
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
    }
}
