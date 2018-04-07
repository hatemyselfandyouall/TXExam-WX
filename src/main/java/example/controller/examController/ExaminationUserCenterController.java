package example.controller.examController;

import example.model.dataobject.Examination;
import example.model.dataobject.User;
import example.model.dataobject.UserExamination;
import example.model.service.ExaminationService;
import example.model.service.QuestionService;
import example.model.service.UserExaminationService;
import example.util.ConstantsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YS-GZD-1495 on 2018/2/6.
 */
@Controller
@RequestMapping("/home/examinationusercenter.htm")
public class ExaminationUserCenterController {

    @Autowired
    UserExaminationService userExaminationService;

    @Autowired
    QuestionService questionService;

    /**
     * 题库列表
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String examManager(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        Map<String,Object> param=new HashMap<>();
        param.put("userId",user.getId());
        param.put("isDelete",0);
        param.put("canTest",1);
        List<UserExamination> userExaminations=userExaminationService.findEntitys(param);
        map.put("userExaminations",userExaminations);
        return "/home/examinationUserCenter";
    }
}
