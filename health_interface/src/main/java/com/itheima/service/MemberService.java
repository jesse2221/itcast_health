package com.itheima.service;

import com.itheima.pojo.Member;

/**
 * @Author
 * @create 2023/8/29 15:56
 * @Version 1.0
 * @Description:
 */
public interface MemberService {
    public Member findByTelephone(String telephone);
    public void add(Member member);
}
