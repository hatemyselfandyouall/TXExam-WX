package example.controller;

import example.model.AdminUser;
import example.model.service.UserService;
import example.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by fox on 2017/9/3.
 */
@Controller
@RequestMapping("/home")
public class IndexController extends ReportCrawlerPorxy {


    @Autowired
    UserService userService;

    @RequestMapping("/index.htm")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        response.setContentType("text/html; charset=UTF-8");
        return "test";
    }
}
