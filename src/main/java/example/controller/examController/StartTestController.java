package example.controller.examController;

import example.model.dataobject.Examination;
import example.model.dataobject.User;
import example.model.dataobject.UserExamination;
import example.model.service.ExaminationService;
import example.model.service.UserExaminationService;
import example.util.ConstantsUtil;
import example.util.DateUtil;
import example.util.logger.WXPayLog;
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
 * Created by fox on 2018/1/3.
 */
@Controller
@RequestMapping("/home/startexam.htm")
public class StartTestController {
    @Autowired
    ExaminationService examinationService;

    @Autowired
    UserExaminationService userExaminationService;


    /**
     * 测试主页面
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        WXPayLog.info("进入页面");
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        WXPayLog.info("调用者"+user.getWNickname());
        Map<String,Object> param=new HashMap<>();
        param.put("isDelete",0);
        List<Examination> examinations=examinationService.findEntitys(param);
        param.put("userId",user.getId());
        for (Examination examination:examinations){
        param.put("examinationId",examination.getId());
            param.put("orderByStr", "create_time desc");
            List<UserExamination> userExaminations=userExaminationService.findEntitys(param);
            if (!userExaminations.isEmpty()) {
                Date now=new Date();
                if (now.compareTo(DateUtil.dateAfter1Day(userExaminations.get(0).getPayTime()))<0) {//超时
                    examination.setCharged(1);
                    UserExamination userExamination= userExaminations.get(0);
                    userExamination.setTestCount((userExamination.getTestCount() != null ? userExamination.getTestCount() : 0) + 1);
                    userExamination.setUpdateTime(new Date());
                    userExaminationService.update(userExamination);
                } else {
                    examination.setCharged(0);
                }
                examination.setUserExamination(userExaminations.get(0));
            } else {
                examination.setCharged(0);
            }
        }
        WXPayLog.info("打开选题页面"+user.getWNickname());
        map.put("examinations",examinations);
        return "examinationChoose";
    }
}