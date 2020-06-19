package com.jun.config.auth;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Author:郁君辉
 * Date:2020-06-10 13:12
 * Description:<描述>
 */
public interface MyUserDetailsServiceMapper  {

    //根据userID查询用户信息
    MyUserDetails findByUserName(@Param("userId") String userId);

    //根据用户ID查询角色列表
    List<String> findRoleByUserName(@Param("userId")String userId);

    //根据角色查询权限
    List<String> findPermissionByRoleCodes(@Param("roleCodes") List<String> roleCodes);



}
