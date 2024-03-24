package com.ksy.controller;

//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ksy.domain.User;
import com.ksy.result.ResultVo;
import com.ksy.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户登录注册
 * @author csy
 * @version 1.0.0
 * @time 2024/3/22 15:16
 */
@RestController
@RequestMapping("/volcanoes/login")
public class LoginController extends BaseController{

    @Autowired
    private LoginService loginService;

    @RequestMapping("/getUserList")
    ResultVo<List<User>> getUserList(){
        int i = 1/0;
        List<User> userList = loginService.findUserlist();
        return ResultVo.success(userList);
    }

}
