package com.lyx.service;

import com.lyx.domain.ResponseResult;
import com.lyx.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
