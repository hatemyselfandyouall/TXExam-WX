package example.controller.examController;

import example.model.dataobject.ChapterExamination;
import example.model.dataobject.Examination;
import example.model.dataobject.Question;
import example.model.service.ChapterExaminationService;
import example.model.service.ExaminationService;
import example.model.service.QuestionService;
import example.util.ResultUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by YS-GZD-1495 on 2018/1/5.
 */
@Controller
@RequestMapping("/exam/chapterExaminationManager.htm")
public class ChapterExaminationController {

    @Autowired
    ExaminationService examinationService;

    @Autowired
    QuestionService questionService;

    @Autowired
    ChapterExaminationService chapterExaminationService;
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
        String id=request.getParameter("id");
        Examination examination=examinationService.getByKey(id);
        Map<String,Object> param=new HashMap<>();
        param.put("examinationId",examination.getId());
        param.put("orderByStr","charpter_id");
        param.put("isDelete",0);
        List<ChapterExamination> chapterExaminations=chapterExaminationService.findEntitys(param);
        Map<Integer, List<ChapterExamination>> ChapterExaminationMap = chapterExaminations.stream().collect(Collectors.groupingBy(item -> item.getCharpterId()));

        map.put("examination",examination);
        map.put("chapterExaminations",ChapterExaminationMap);
        return "/exam/chapterExaminationManager";
    }

    @RequestMapping(method = RequestMethod.POST,params = "action=editExamination")
    public void editExamination(HttpServletRequest request, HttpServletResponse response, @ModelAttribute ChapterExamination chapterExamination) {
        JSONObject result=new JSONObject();
        if (!chapterExaminationService.checkChapSetting(chapterExamination)){
            result.put("ret_code",0);
            result.put("ret_msg","该章节出题数超限");
            try {
                ResultUtil.writeResult(response,result.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (chapterExaminationService.SaveChapSetting(chapterExamination)!=0){
            result.put("ret_code",1);
            result.put("ret_msg","成功");
        }else {
            result.put("ret_code",0);
            result.put("ret_msg","失败");
        }
        try {
            ResultUtil.writeResult(response,result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
