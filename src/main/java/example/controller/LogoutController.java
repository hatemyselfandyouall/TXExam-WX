package example.controller;

/**
 * Created by fox on 2018/1/13.
 */

import example.model.service.UserService;
import example.util.ConstantsUtil;
import example.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

import example.model.service.UserService;
import example.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;

/**
 * Created by fox on 2017/9/3.
 */
@Controller
@RequestMapping("/logout.htm")
public class LogoutController extends ReportCrawlerPorxy {



    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws UnsupportedEncodingException {
        request.getSession().removeAttribute(ConstantsUtil.ADMINUSER);
        return "redirect:/home/startExam.htm";
    }
}
