package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author
 * @create 2023/8/29 16:07
 * @Version 1.0
 * @Description:
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {
    @Autowired
    private  MemberDao memberDao;
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    public void add(Member member) {
        memberDao.add(member);
    }

    public List<Integer> findMemberCountByMonth(List<String> mouth) {
        List<Integer> memberCount = new ArrayList<Integer>();
        for (String date : mouth) {
            date+=".31";//格式：2019.04.31
            Integer count = memberDao.findMemberCountBeforeDate(date);
            memberCount.add(count);
        }
        return memberCount;
    }
}
