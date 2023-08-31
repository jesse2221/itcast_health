package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @Author
 * @create 2023/8/31 17:48
 * @Version 1.0
 * @Description:
 */
public interface PermissionDao {
    public Set<Permission> findByRoleId(Integer RoleId);
}
