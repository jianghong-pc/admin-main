package com.qianmi.admin.action.user;

import com.qianmi.admin.action.BaseController;
import com.qianmi.admin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;



}
