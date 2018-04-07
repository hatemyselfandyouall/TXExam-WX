package example.controller;

import example.model.dataobject.User;
import example.model.service.UserService;
import example.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 开始页面
 * Created by YS-GZD-1495 on 2017/11/23.
 */
@Controller
@RequestMapping("/home/start.htm")
public class StartController {

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        if(user==null){
            user=userService.getByKey("1");
        }
        map.put("user", user);
        request.getSession().setAttribute(ConstantsUtil.ADMINUSER, user);
        response.setContentType("text/html; charset=UTF-8");
        return "start";
    }
}
