package com.lyx;

import com.lyx.domain.User;
import com.lyx.mapper.MenuMapper;
import com.lyx.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.util.Password;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    private UserMapper userMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private MenuMapper menuMapper;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void getBCryptPasswordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("123");
        // $2a$10$zKT/UPs3pqpYJpUgD/Bm5ubO5xazgwTUoDFuPhcaueC4hPSPwWKLO
//        System.out.println(encode);
        System.out.println(passwordEncoder.matches("123", "$2a$10$zKT/UPs3pqpYJpUgD/Bm5ubO5xazgwTUoDFuPhcaueC4hPSPwWKLO"));
    }

    @Test
    public void TestSelectPermsByUserId() {
        List<String> list = menuMapper.selectPermsByUserId((long) 1);
        System.out.println(list);
    }
}
