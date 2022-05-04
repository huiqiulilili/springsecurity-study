package com.lyx.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lyx.domain.LoginUser;
import com.lyx.domain.User;
import com.lyx.mapper.MenuMapper;
import com.lyx.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //  查询用户信息
        User user = userMapper.selectOne(new QueryWrapper<User>().eq("user_name", username));

        // 如果没有查询到用户，就抛出异常
        if (StringUtils.isEmpty(user)) {
            throw new RuntimeException("用户名或者密码错误");
        }
        // TODO 查询对应的权限信息
//        List<String> list = new ArrayList<>(Arrays.asList("test","admin"));
        List<String> list = menuMapper.selectPermsByUserId(user.getId());

        // 把数据封装成UserDetails返回
        return new LoginUser(user,list);
    }
}
