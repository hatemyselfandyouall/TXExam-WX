package example.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;

import example.model.dataobject.User;
import example.model.service.UserService;
import example.model.service.UserServiceImpl;
import example.util.ConstantsUtil;
import net.sf.json.JSONObject;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.axis.client.Call;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletRequest;
import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

/**
 * @author lintc
 * @date 2016年8月17日
 */
public abstract class ReportCrawlerPorxy {

    private static final Logger LOG = Logger.getLogger(ReportCrawlerPorxy.class);

    @Autowired
    UserService userService;


    /**
     * HTTP请求
     *
     * @param msg
     * @param wsdlUrl
     * @param action
     * @return
     */
    public static String invoker(String msg, String wsdlUrl, String action) {
        String xmls = null;
//		LOG.info("MSG： " + msg);
        HttpPost post = new HttpPost(wsdlUrl);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpEntity entity = new StringEntity(msg, "UTF-8");
            post.setHeader("Content-Type", "text/xml; charset=utf-8");
            if (StringUtils.isNotBlank(action)) {
                {
                    post.setHeader("SOAPAction", action);
                }
            }
            post.setEntity(entity);
            HttpResponse response = httpclient.execute(post);
            xmls = EntityUtils.toString(response.getEntity()).toString();
//		    LOG.info("XML： " + xmls);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xmls;
    }

    /**
     * 调用微信接口登录
     * @param request
     * @param code
     * @return
     */
    public  JSONObject  checkLogin(HttpServletRequest request,String code) throws UnsupportedEncodingException {
        JSONObject result=new JSONObject();
        String codes=getToken(code);
        JSONObject json=JSONObject.fromObject(codes) ;
        String accessToken=json.getString("access_token");
        String openId=json.getString("openid");
        Map<String,Object> param=new HashMap<>();
        param.put("openid",openId);
        List<User> users=userService.findEntitys(param);

        if (users.size()>0) {
            request.getSession().setAttribute(ConstantsUtil.ADMINUSER, users.get(0));
        }else {
            String userMsg=getUserMsg(accessToken, openId);
            userMsg=new String(userMsg.getBytes("ISO-8859-1"),"UTF-8");
            JSONObject user=JSONObject.fromObject(userMsg);
            User newUser=new User();
            newUser.setCity(user.optString("city"));
            newUser.setCountry(user.optString("country"));
            newUser.setWNickname(user.optString("nickname"));
            newUser.setWNickname(newUser.getWNickname().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));
            newUser.setSex(Integer.valueOf(user.optString("sex") != null ? user.optString("sex") : "0"));
            newUser.setProvince(user.optString("province"));
            newUser.setPrivilege(user.optString("privilege"));
            newUser.setUnionid(user.optString("unionid"));
            newUser.setOpenid(user.optString("openid"));
            newUser.setLanguage(user.optString("language"));
            newUser.setHeadimgurl(user.optString("headimgurl"));
            newUser.setCreateTime(new Date());
            userService.insert(newUser);
            request.getSession().setAttribute(ConstantsUtil.ADMINUSER, newUser);
            result.put("user", userMsg);
        }
        request.getSession().setMaxInactiveInterval(31536000);//设置一年的过期时间,对手机端来说应该没问题
        result.put("json", json);
        result.put("userList",users);
        result.put("userListSize",users.size());
        result.put("openid",openId);

        return result;
//        return JSONObject.fromObject(userMsg);
    }
    public  String getToken(String code){
        String url= ConstantsUtil.GET_TOKEN_URL;
        url=url.replace("APPID",ConstantsUtil.APPID);
        url=url.replace("SECRET",ConstantsUtil.APPSERECT);
        url=url.replace("CODE",code);
        return  invoker("",url,null);
    }

    public  String getUserMsg(String accessToken,String openId){
        String url=ConstantsUtil.GET_MSG_URL;
        url=url.replace("ACCESS_TOKEN",accessToken);
        url=url.replace("OPENID",openId);
        return  invoker("", url, null);
    }
}
