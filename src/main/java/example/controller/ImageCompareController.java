package example.controller;

import com.ctc.wstx.util.StringUtil;
import example.model.dataobject.Calligraphy;
import example.model.dataobject.User;
import example.model.dataobject.UserLearn;
import example.model.service.CalligraphyService;
import example.model.service.UserLearnService;
import example.model.service.UserService;
import example.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpRequest;
import org.codehaus.plexus.util.StringUtils;
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
 * Created by fox on 2017/9/3.
 */
@Controller
@RequestMapping("/home/image.htm")
public class ImageCompareController {

    @Autowired
    CalligraphyService calligraphyService;

    @Autowired
    UserLearnService userLearnService;

    @Autowired
    UserService userService;

    /**
     * 初始页面
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        map.put("user", user);
        response.setContentType("text/html; charset=UTF-8");
        return "startLearn";
    }

    /**
     * 默认字体-选择课程页面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=learnPage")
    public String  learnPage(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        User user= (User) request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        List<Calligraphy> calligraphies=calligraphyService.findEntitys(map);
        for (Calligraphy calligraphy:calligraphies){
            map.put("userId", user.getId());
            map.put("CalligraphyId", calligraphy.getId());
            List<UserLearn> userLearns=userLearnService.findEntitys(map);
            if (userLearns.size()>0) {
                calligraphy.setUserLearn(userLearns.get(0));
            }else {
                UserLearn userLearn=new UserLearn();
                userLearn.setLearnProgress(0);
                calligraphy.setUserLearn(userLearn);
            }
        }
        map.clear();
        map.put("calligraphies", calligraphies);
        return "learnPage";
    }

    /**
     * 自传图片页面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=comparePage")
    public String  comparePage(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        return "comparePage";
    }



    /**
     * 展示图片页面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=viewImagePage")
    public String  show(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        String id=request.getParameter("id");
        Calligraphy calligraphy=calligraphyService.getByKey(id);
        List<Integer> images=new ArrayList<>();
        for (int i=0;i<calligraphy.getCaSize();i++){
            images.add(i+1);
        }
        map.put("calligraphy",calligraphy);
        map.put("images",images);
        map.put("calligraphyId", id);
        return "showImages";
    }
    /**
     * 展示图片页面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=testImagePage")
    public String  testimage(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        String calligraphyId=request.getParameter("calligraphyId");
        Calligraphy calligraphy=calligraphyService.getByKey(calligraphyId);
        String id=request.getParameter("id");
        map.clear();
        map.put("calligraphy",calligraphy);
        map.put("calligraphyId",calligraphyId);
        map.put("imgId",id);
        return "testImage";
    }

    /**
     * 调用打分功能
     * @param request
     * @param response
     * @param map
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=uploadImage")
    public void  uploadImage(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        String image=request.getParameter("image");
        String imgId=request.getParameter("imgId");
        String calligraphyId=request.getParameter("calligraphyId");
        Calligraphy calligraphy=calligraphyService.getByKey(calligraphyId);
        String path=ConstantsUtil.Image_PATH+File.separator+"temp"+File.separator;
        String calligraphyImage=ConstantsUtil.Image_PATH+File.separator+calligraphy.getImgPath()+File.separator+imgId+"."+calligraphy.getImgType();
        String proImage=path+"pro"+PDFParseUtil.getPDFParser().getTimeStamp()+"."+calligraphy.getImgType();
        PDFParseUtil.getPDFParser().copyFile(calligraphyImage,proImage);
        image = image.split("base64,")[1];
        String type = request.getParameter("type");;
        byte[] img=Base64.getDecoder().decode(image.getBytes());
        String testImage=path+PDFParseUtil.getPDFParser().getTimeStamp()+"."+type;
        PDFParseUtil.getPDFParser().saveAllFile(img, path, testImage);
        JSONObject result=new JSONObject();
        ImageCompareUtil.porCompareImage(proImage,testImage);
        String compareResult=ImageCompareUtil.compareImage(proImage, testImage);
        result.put("ret_code", 0);
        result.put("ret_msg", compareResult);
        JSONObject sessionResult=new JSONObject();
        sessionResult.put("result", result);
        sessionResult.put("imgId", imgId);
        sessionResult.put("calligraphyId", calligraphyId);
        sessionResult.put("proImage", proImage);
        sessionResult.put("testImage", testImage);
        request.getSession().setAttribute("sessionResult",sessionResult);
//        request.getSession().setAttribute("result", result);
//        request.getSession().setAttribute("imgId",imgId);
//        request.getSession().setAttribute("calligraphyId",calligraphyId);
//        request.getSession().setAttribute("proImage",proImage);
//        request.getSession().setAttribute("testImage",testImage);
        ResultUtil.writeResult(response, "");
    }

    /**
     * 打分结果页面
     * @param request
     * @param response
     * @param map
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.GET,params = "action=compareResult")
    public String  compareResult(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        User user= (User) request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        JSONObject sessionResult= (JSONObject) request.getSession().getAttribute("sessionResult");
        JSONObject result= (JSONObject) sessionResult.get("result");
        String imgId= (String) sessionResult.get("imgId");
        String calligraphyId= (String) sessionResult.get("calligraphyId");
        String proImage=(String) sessionResult.get("proImage");
        String testImage=(String) sessionResult.get("testImage");
        String proStr = ImageUtil.getImgPrefix(proImage) + ImageUtil.ImgUrlToBase64(proImage);
        String testStr = ImageUtil.getImgPrefix(testImage) + ImageUtil.ImgUrlToBase64(testImage);
        Integer nowId=Integer.valueOf(imgId)-1;
            UserLearn userLearn;
            Map<String,Object> param=new HashMap<>();
            param.put("userId",user.getId());
            param.put("calligraphyId",calligraphyId);
            List<UserLearn> userLearns=userLearnService.findEntitys(param);
            if (userLearns.size()>0){
                userLearn=userLearns.get(0);
                userLearn.setLearnProgress(Integer.valueOf(imgId));
                userLearnService.update(userLearn);
            }else {
                userLearn=new UserLearn();
                userLearn.setUserId(user.getId());
                userLearn.setLearnProgress(Integer.valueOf(imgId));
                userLearn.setCalligrapyId(Integer.valueOf(calligraphyId));
                userLearnService.insert(userLearn);
            }

        map.put("result",result);
        map.put("imgId",imgId);
        map.put("calligraphyId",calligraphyId);
        map.put("nextId",Integer.valueOf(imgId)+1);
        map.put("proStr",proStr);
        map.put("testStr",testStr);
        request.getSession().removeAttribute("sessionResult");
        return "compareResult";
    }



}
