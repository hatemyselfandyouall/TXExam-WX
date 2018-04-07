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

@Controller
@RequestMapping("/home/preExamination.htm")
public class preExaminationController {


    @RequestMapping(method = RequestMethod.GET)
    public String fail(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws IOException {
        String id = request.getParameter("id");
        map.put("id",id);
        return "/home/preExamination";
    }
}