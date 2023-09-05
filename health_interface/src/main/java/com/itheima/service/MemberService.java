package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

/**
 * @Author
 * @create 2023/8/29 15:56
 * @Version 1.0
 * @Description:
 */
public interface MemberService {
    public Member findByTelephone(String telephone);
    public void add(Member member);
    public List<Integer> findMemberCountByMonth(List<String> mouth);
}
