package example.controller.examController;

import example.model.dataobject.Examination;
import example.model.dataobject.Question;
import example.model.dataobject.User;
import example.model.dataobject.UserExam;
import example.model.service.ExaminationService;
import example.model.service.QuestionService;
import example.util.ConstantsUtil;
import example.util.PDFParseUtil;
import example.util.ResultUtil;
import example.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by YS-GZD-1495 on 2017/12/25.
 */
@Controller
@RequestMapping("/exam/exammanager.htm")
public class ExammanagerController {
    @Autowired
    ExaminationService examinationService;

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
        map.put("isDelete",0);
        List<Examination> examinations=examinationService.findEntitys(map);
        map.put("examinations",examinations);
        return "/exam/examList";
    }

    /**
     * 添加-编辑题库
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=addExamination")
    public String addExamination(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        String id=request.getParameter("id");
        Examination examination;
        if (id!=null){
            examination=examinationService.getByKey(id);
            map.put("examination",examination);
            map.put("examinationId",id);
        }else {
            map.put("examinationId", 0);
        }
        return "/exam/addExamination";
    }

    /**
     * 添加-编辑题库
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=deleteExamination")
    public void deleteExamination(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        JSONObject result=new JSONObject();
        String id=request.getParameter("id");
        if (id!=null){
            Integer flag=examinationService.deleteExamination(id);
            if (flag.equals(1)){
                result.put("ret_code",1);
                result.put("ret_msg","成功");
            }else {
                result.put("ret_code",0);
                result.put("ret_msg","失败!");
            }
        }else {
            result.put("ret_code",0);
            result.put("ret_msg","失败!未取得id");
        }
        try {
            ResultUtil.writeResult(response, result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传图片功能
     * @param request
     * @param response
     * @param map
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=submitExamination")
    public void  submitExamination(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Examination examination,ModelMap map)  {
        JSONObject result=new JSONObject();
        Integer flag=examinationService.insertExamination(examination);
        if (!flag.equals(0)){
            result.put("ret_code",1);
            result.put("ret_resultId",flag);
            result.put("ret_msg","成功");
        }else {
            result.put("ret_code",0);
            result.put("ret_msg","失败!");
        }
        try {
            ResultUtil.writeResult(response, result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 上传图片功能
     * @param request
     * @param response
     * @param map
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=examinationFinal")
    public String  examinationFinal(HttpServletRequest request,HttpServletResponse response,@ModelAttribute Examination examination,ModelMap map)  {
        request.getParameter("results");
      return "examinationFinal";
    }

}
