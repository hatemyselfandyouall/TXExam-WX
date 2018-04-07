package example.controller.examController;

import example.model.dataobject.Examination;
import example.model.service.ExaminationService;
import example.util.constant.ExaminationConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by YS-GZD-1495 on 2018/3/12.
 */
@Controller
@RequestMapping("/home/fail.htm")
public class FailController {

    @Autowired
    ExaminationService examinationService;

    @RequestMapping(method = RequestMethod.GET)
    public String fail(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws IOException {
        String id = request.getParameter("id");
        Examination examination = examinationService.getByKey(id);
        Integer type=Integer.valueOf(request.getParameter("type"));
        String resultWord="";
        if (type.equals(ExaminationConstant.EXIT_TO_MUCH_TIME)){
            resultWord="对不起，最多只能重复进行三次测试，您已超出限制";
        }
        if (type.equals(ExaminationConstant.OUT_TIME)){
            resultWord="对不起，正式考试时间限定为30分，您已答题超时";
        }
        map.put("resultWord",resultWord);
        return "/home/fail";
    }
}
