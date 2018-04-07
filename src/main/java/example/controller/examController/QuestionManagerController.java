package example.controller.examController;

/**
 * Created by YS-GZD-1495 on 2017/12/26.
 */

import example.model.dataobject.ChapterExamination;
import example.model.dataobject.Examination;
import example.model.dataobject.Question;
import example.model.service.ExaminationService;
import example.model.service.QuestionService;
import example.util.ConstantsUtil;
import example.util.ExcelUtil;
import example.util.ResultUtil;
import net.sf.json.JSONObject;
import example.util.ResultUtil;
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
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YS-GZD-1495 on 2017/12/25.
 */
@Controller
@RequestMapping("/exam/questionManager.htm")
public class QuestionManagerController {
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
        String id=request.getParameter("id");
            Examination examination=examinationService.getByKey(id);
            Map<String,Object> param=new HashMap<>();
            param.put("examinationId", id);
            param.put("isDelete",0);
            List<Question> questions=questionService.findEntitys(param);
            map.put("examination",examination);
            map.put("questions",questions);
            map.put("examinationId",id);
        return "/exam/questionList";
    }

    /**
     * 添加-编辑题库
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=addQuestion")
    public String addQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        String id=request.getParameter("id");
        Question question;
        if (id!=null){
            question=questionService.getByKey(id);
            Examination examination=examinationService.getByKey(question.getExaminationId().toString());
            map.put("examination",examination);
            map.put("question",question);
        }else {
            String examinationId=request.getParameter("examinationId");
            Examination examination=examinationService.getByKey(examinationId);
            map.put("examination",examination);
            map.put("questionId", 0);
        }
        return "/exam/addQuestion";
    }
    /**
     * 批量上传
     * @param request
     * @param map
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST, params = "action=uploadQuestions")
    public void singleUpload(MultipartHttpServletRequest request, ModelMap map,
                               HttpServletResponse response) throws IOException {
        JSONObject result = new JSONObject();
        result.put("ret_code", 0);
        String id = request.getParameter("id");
        InputStream is = null;
        try {
            MultipartFile file = request.getFile("pdfFile");
            String[] fileName = file.getOriginalFilename().split("\\.");//获取文件名
            is = file.getInputStream();
                String filePath = ConstantsUtil.FILE_TEMP_PATH + "/" + file.getOriginalFilename();
                File desFile = new File(filePath);
                if (!desFile.getParentFile().exists()) {
                    desFile.mkdirs();
                }
            file.transferTo(desFile);
            List<Question> questions = ExcelUtil.parseXML2Questions(filePath,1);
            questionService.saveQuestions(questions,id);
        }catch (Exception e) {
            result.put("ret_code", 1);
            result.put("msg", "文件或程序异常");
            e.printStackTrace();
        } finally {
            is.close();
        }
        ResultUtil.writeResult(response, result.toString());
    }
    /**
     * 添加-编辑题库
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=deleteQuestion")
    public void deleteQuestion(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        JSONObject result=new JSONObject();
        String id=request.getParameter("id");
        if (id!=null){
            Integer flag=questionService.deleteQuestion(id);
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


    @RequestMapping(method = RequestMethod.POST,params = "action=editQuestion")
    public void editExamination(HttpServletRequest request, HttpServletResponse response, @ModelAttribute Question question) {
        JSONObject result=new JSONObject();
        if (questionService.insertQuestion(question)!=0){
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
