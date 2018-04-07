package example.controller;

import example.model.dataobject.Calligraphy;
import example.model.dataobject.User;
import example.model.dataobject.UserExam;
import example.model.dataobject.UserLearn;
import example.model.service.*;
import example.util.ConstantsUtil;
import example.util.ImageCompareUtil;
import example.util.PDFParseUtil;
import example.util.ResultUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;

/**
 * 管理我的范字-相关功能
 * Created by YS-GZD-1495 on 2017/11/23.
 */
@Controller
@RequestMapping("/home/myExam.htm")
public class myExamController extends BasicController{

    @Autowired
    CalligraphyService calligraphyService;

    @Autowired
    UserLearnService userLearnService;

    @Autowired
    UserService userService;

    @Autowired
    UserExamService userExamService;

    @Autowired
    UserWorkService userWorkService;

    /**
     * 查看范字页面
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=showImages")
    public String index(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        map.put("user", user);
        response.setContentType("text/html; charset=UTF-8");
        return "uploadMyExam";
    }
    /**
     * 上传范字页面
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String uploadPage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        response.setContentType("text/html; charset=UTF-8");
        return "uploadMyExam";
    }

    /**
     * 上传图片功能
     * @param request
     * @param response
     * @param map
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=uploadImage")
    public void  uploadImage(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        User user= (User) request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        //base64加密后的文件
        String image=request.getParameter("image");
        //文件类型
        String imageType=request.getParameter("imageType");
        Map<String,Object> param=new HashMap<>();
        param.put("userId",user.getId());
        UserExam userExam;
        String path=ConstantsUtil.Image_PATH+ File.separator+"user"+user.getId()+File.separator;
        image = image.split("base64,")[1];
        byte[] img= Base64.getDecoder().decode(image.getBytes());
        List<UserExam> userExams=userExamService.findEntitys(param);
        //获取用户范例数据
        if (userExams.size()==0){
            userExam=new UserExam();
            userExam.setUserId(user.getId());
            userExam.setCreateTime(new Date());
            userExam.setUpdateTime(new Date());
            userExam.setExSize(0);
            userExam.setImgPath(path);
            userExam.setExSize(0);
            userExam.setImgType(imageType);
        }else {
            userExam=userExams.get(0);
        }
        userExam.setExSize(userExam.getExSize()+1);
        //1.jpg
        String testImage=path+userExam.getExSize()+"."+imageType;
        PDFParseUtil.getPDFParser().saveAllFile(img, path, testImage);
        userExamService.upload(userExam);
        JSONObject result=new JSONObject();
        result.put("ret_code", 0);
        ResultUtil.writeResult(response, result.toString());
    }
}