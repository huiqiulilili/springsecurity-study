package com.lyx.service.impl;

import com.lyx.domain.LoginUser;
import com.lyx.domain.ResponseResult;
import com.lyx.domain.User;
import com.lyx.service.LoginService;
import com.lyx.utils.JwtUtil;
import com.lyx.utils.RedisCache;
import com.lyx.utils.WebUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        // AuthenticationManager authenticate进行用户认证
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        // 如果认证没通过，给出对应的提示
        if(StringUtils.isEmpty(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        // 如果认证通过，使用userId 生成一个 jwt,jwt存入 ResponseResult 进行返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);

        // 把完整的用户信息存入 redis userId 作为key
        redisCache.setCacheObject("login:"+userid,loginUser);

        return new ResponseResult(200,"登陆成功",map);
    }

    @Override
    public ResponseResult logout() {
        // 获取 SecurityContextHolder中的用户id
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();

        // 删除redis中的值
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"注销成功");
    }
}
