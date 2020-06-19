package com.jun.config.auth.jwt;

import com.jun.config.exception.CustomException;
import com.jun.config.exception.CustomExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:郁君辉
 * Date:2020-06-17 9:00
 * Description:<描述>
 */
@Service
public class JwtAuthService {

    @Autowired
    AuthenticationManager authenticationManager;


   @Resource
    UserDetailsService userDetailsService;

   @Autowired
   JwtTokenUtil jwtTokenUtil;
    /**
     * 登陆认证换取jwt令牌
     * @param username
     * @param password
     * @return jwt
     * @throws CustomException
     */
    public String login(String username, String password) throws CustomException {

        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authenticate = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        } catch (AuthenticationException e) {
            throw new CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名或者密码不正确");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return  jwtTokenUtil.generateToken(userDetails);
    }

    public String refreshToken(String oldToken){
            if (!jwtTokenUtil.isTokenExpired(oldToken)){
                return jwtTokenUtil.refreshToken(oldToken);
            }
            return null;
            
    }

}
