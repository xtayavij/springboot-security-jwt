package com.jun;

import com.jun.config.auth.MyUserDetails;
import com.jun.config.auth.MyUserDetailsServiceMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@SpringBootTest
class SpringbootSecurityJWTApplicationTests {

    @Resource
    PasswordEncoder passwordEncoder;

    @Autowired
    MyUserDetailsServiceMapper myUserDetailsServiceMapper;
    @Test
    void contextLoads() {
        String ss=BCrypt.hashpw("123456",BCrypt.gensalt());
        System.out.println(ss);
        boolean checkpw = BCrypt.checkpw("123456", "$2a$10$UfOXeejOxoIP8GLGHPpJhO/Z/XJpFKabmAgRGTtBJfNw7wsea9P.e");
        System.out.println(checkpw);
    }


    @Test
    void testSql1(){
        MyUserDetails admin = myUserDetailsServiceMapper.findByUserName("admin");
        System.out.println(admin);
    }
    @Test
    void testSql2(){
    List<String> admin = myUserDetailsServiceMapper.findRoleByUserName("admin");
        System.out.println(admin);
    }

    @Test
    void random(){

        double ran = (Math.random()*9000+1000);//生成4位随机数

        System.out.println(StringUtils.toString((Math.random()*9000+1000)));
    }

}
