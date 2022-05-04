package com.lyx.controller;


import com.lyx.domain.ResponseResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("hello")
    @PreAuthorize("hasAuthority('system:dept:list')")
    public String hello() {
        return "hello";
    }


    @RequestMapping("/testCros")
    public ResponseResult testCros() {
        return new ResponseResult(200,"testCros");
    }

}
