package com.qianmi.admin.action;

import com.qianmi.admin.common.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

/**
 * Description：用户登陆的控制器
 */
@CrossOrigin
@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {Constants.PRODUCES})
    @ResponseBody
    public Map<String, Object> login(String username, String password, RedirectAttributes redirectAttributes){
        return null;
    }

    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public Map<String, Object> logout(RedirectAttributes redirectAttributes ){
        return null;
    }

}
