package com.jun.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author:郁君辉
 * Date:2020-06-10 16:20
 * Description:<描述>
 */
@Component
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    MyUserDetailsServiceMapper myUserDetailsServiceMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //加载基础用户信息
        MyUserDetails myUserDetails = myUserDetailsServiceMapper.findByUserName(username);

        //加载用户列表
        List<String> roleCodes = myUserDetailsServiceMapper.findRoleByUserName(username);

        //通过角色加载用户资源权限列表
        List<String> permission = myUserDetailsServiceMapper.findPermissionByRoleCodes(roleCodes);

        //角色是一个特殊的权限，ROLE_前缀
        roleCodes=roleCodes.stream()
                .map(rc ->"ROLE_"+rc )
                .collect(Collectors.toList());

        permission.addAll(roleCodes);

            myUserDetails.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        String.join(",",permission)
                )
        );

        return myUserDetails;
    }
}
