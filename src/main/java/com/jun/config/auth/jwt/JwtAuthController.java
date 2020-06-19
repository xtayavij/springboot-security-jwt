package com.jun.config.auth.jwt;

import com.jun.config.exception.AjaxResponse;
import com.jun.config.exception.CustomException;
import com.jun.config.exception.CustomExceptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Author:郁君辉
 * Date:2020-06-17 9:00
 * Description:<描述>
 */
@RestController
public class JwtAuthController {

    @Autowired
    JwtAuthService jwtAuthService;

    @RequestMapping(value = "/authentication")
    public AjaxResponse login(@RequestBody Map<String, String> map) {
        String username = map.get("username");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return AjaxResponse.error(new
                    CustomException(CustomExceptionType.USER_INPUT_ERROR, "用户名和密码不能为空"));
        }
        try {
            return AjaxResponse.success(jwtAuthService.login(username, password));
        } catch (CustomException e) {
            return AjaxResponse.error(e);
        }

    }

    @RequestMapping(value = "/refreshtoken")
    public AjaxResponse refresh(@RequestHeader("${jwt.header}") String token) {
        return AjaxResponse.success(jwtAuthService.refreshToken(token));
    }
}
