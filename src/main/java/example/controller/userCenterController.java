package example.controller;

import example.model.dataobject.User;
import example.model.dataobject.UserExam;
import example.model.dataobject.UserLearn;
import example.model.service.CalligraphyService;
import example.model.service.UserExamService;
import example.model.service.UserLearnService;
import example.model.service.UserService;
import example.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YS-GZD-1495 on 2017/11/15.
 */
@Controller
@RequestMapping("/home/userCenter.htm")
public class userCenterController {

    @Autowired
    UserService userService;
    @Autowired
    CalligraphyService calligraphyService;
    @Autowired
    UserLearnService userLearnService;

    @Autowired
    UserExamService userExamService;

    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        map.put("user", user);
        Map<String,Object> param=new HashMap<>();
        param.put("userId",user.getId());
        param.put("calligrapyId", 2);
        List<UserLearn> userLearns=userLearnService.findEntitys(param);
        UserLearn userLearn;
        if(userLearns.size()>0){
            userLearn=userLearns.get(0);
        }else {
            userLearn=new UserLearn();
            userLearn.setUserId(user.getId());
            userLearn.setCalligrapyId(2);
            userLearn.setAverageScore(0);
            userLearn.setHighestScore(0);
            userLearnService.insert(userLearn);
        }
        UserExam userExam;
        List<UserExam> userExams=userExamService.findEntitys(param);
        if(userExams.size()>0){
            userExam=userExams.get(0);
        }else {
            userExam=new UserExam();
            userExam.setUserId(user.getId());
            userExam=new UserExam();
            userExam.setUserId(user.getId());
            userExam.setCreateTime(new Date());
            userExam.setUpdateTime(new Date());
            userExam.setExSize(0);
            userExamService.insert(userExam);
        }
        map.put("user",user);
        map.put("userLearn",userLearn);
        map.put("userExam",userExam);
        response.setContentType("text/html; charset=UTF-8");
        return "userCenter";
    }
}
