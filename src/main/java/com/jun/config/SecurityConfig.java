package com.jun.config;

import com.jun.config.auth.MyAuthenticationSuccessHandler;
import com.jun.config.auth.MyUserDetailsService;
import com.jun.config.auth.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * Author:郁君辉
 * Date:2020-06-06 11:00
 * Description:<描述>
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Autowired
    DataSource dataSource;

    @Autowired
    JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    /**
     * 简单的httpbasic()认证
     *
     * @param
     * @throws Exception
     */
/*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .and()
                .authorizeRequests().anyRequest()
                .authenticated();
    }
 */


    /**
     * 内存生成用户名密码
     *
     * @param auth
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
       /* auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("user")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123456"))
                .authorities("sys:log", "sys:user")
                //.roles("admin")
                .and()
                .passwordEncoder(passwordEncoder());//配置BCrypt加密*/

        auth.userDetailsService(myUserDetailsService).passwordEncoder(passwordEncoder());

    }


    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //返回BCrypt加密
    }

    /**
     * 表单提交
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringAntMatchers("/authentication")

                //.csrf().disable() //禁用跨站csrf攻击防御，后面的章节会专门讲解
                .and()
                .cors()//跨域资源访问
                .and()
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .and()
                .authorizeRequests()
                .antMatchers("/authentication", "/refreshtoken").permitAll()
                .antMatchers("/index").authenticated()
                .anyRequest()
                .access("@rbacService.hasPermission(request,authentication)")
                .and()
                .sessionManagement() /*会话创建策略:
                            1.IF_REQUIRED(默认)如果需要就创建一个session登录时
                            2.stateless 绝对不会创建session,也不使用session （用于token认证.REST API）
                            3.never  不会创建session 但是应用中其他地方创建了session那么将使用它
                            4.always  如果没有session 就创建一个*/
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);//无状态


    }


    /**
     * 记住密码功能：把用户信息记录在数据库中
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl TokenRepository = new JdbcTokenRepositoryImpl();
        TokenRepository.setDataSource(dataSource);
        return TokenRepository;
    }

    //初始化AuthenticationManager
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    //Security推荐CORS跨域资源访问方法
  /*  @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        configuration.applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/


}
