package example.controller.examController;

import example.controller.ReportCrawlerPorxy;
import example.model.dataobject.*;
import example.model.service.ExaminationService;
import example.model.service.UserExaminationService;
import example.util.*;
import example.util.logger.WXPayLog;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.http.HttpClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 接入微信支付----PC端不能调用微信浏览器功能，看情况给用户一个二维码玩玩好了
 * Created by YS-GZD-1495 on 2018/2/6.
 */
@Controller
@RequestMapping("/home/payMoney.htm")
public class PayMoneyController extends ReportCrawlerPorxy {
    @Autowired
    ExaminationService examinationService;
    @Autowired
    UserExaminationService userExaminationService;

    /**
     * 第二部js支付
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request,HttpServletResponse response,ModelMap map) {
        String prepay_id=request.getParameter("prepay_id");
        SortedMap<String, String> param = new ConcurrentSkipListMap<>() ;
        param.put("appId",ConstantsUtil.APPID);
        param.put("timeStamp", DateUtil.getTimeStamp());
        param.put("nonceStr", WXPayUtil.generateUUID());//随机字符串
        param.put("package","prepay_id="+prepay_id);
        param.put("signType","MD5");
        String sign = OrderUtil.createSign(param);
        param.put("paySign",sign);
        param.put("prepayId",prepay_id);
        String xml=OrderUtil.getRequestXml(param);
        WXPayLog.info("发送给微信的报文：" + xml);
        String payType=request.getParameter("payType");
        if("1".equals(payType)){
            map.put("codeUrl",request.getParameter("codeUrl"));
        }
        map.put("payType",payType);
        map.put("param",param);
        return "/home/payMoney";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET,params = "action=checkCA")
    public String checkCA(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) {
        response.setContentType("text/html; charset=UTF-8");
        JSONObject result=new JSONObject();
        String url="https://apitest.mch.weixin.qq.com/sandboxnew/pay/getsignkey";
        SortedMap<String, String> map = new ConcurrentSkipListMap<>() ;
        map.put("nonce_str", WXPayUtil.generateUUID());//随机字符串
        map.put("mch_id", ConstantsUtil.MCH_ID);//商户平台id
        String sign = OrderUtil.createSign("UTF-8",map);
        map.put("sign",sign);
        String res="";
        String xml=OrderUtil.getRequestXml(map);
        try {
            res = invoker(xml,"https://api.mch.weixin.qq.com/pay/unifiedorder",null);
            res=new String(res.getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e) {
            //TODO
            return null;
        }
        Map<String, String>  WXResult=new HashMap<>();
        try {
            WXResult=XMLUtil.doXMLParse(res);
        }catch (JDOMException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        result.put("WXResult",WXResult);
        modelMap.put("result",result);
        return result.toString();
    }

    /**
     * 第一步，预支付申请
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=prePayRequest")
    public void prePayRequest(HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        JSONObject result=new JSONObject();
        User user=(User)request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        if (user.getOpenid()==null) {
            user.setOpenid("ofDLw1QUnTdqF8ikmwNbF_ulIKeY");
        }


        String examinationId=request.getParameter("examinationId");
        Examination examination=examinationService.getByKey(examinationId);
        WXPayLog.info("用户"+user.getWNickname()+"开始购买商品"+examination.getName());
        String payType=request.getParameter("payType");
        String nonceStr = "5K8264ILTKCH16CQ2502SI8ZNMTM67VS";//暂时不变
        String body="CommunicationExamination-Order";
        String ip= NetworkUtil.getIpAddress(request);
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip="183.129.254.162";
        }
        String orderSn= OrderUtil.getOrderNO();
        SortedMap<String, String> map = new ConcurrentSkipListMap<>() ;
        map.put("body", body);//商品描述
        map.put("mch_id", ConstantsUtil.MCH_ID);//商户平台id
        map.put("appid", ConstantsUtil.APPID);//公众号id
        map.put("nonce_str", WXPayUtil.generateUUID());//随机字符串
        map.put("notify_url", ConstantsUtil.NOTIFY_URL);//异步回调api
        map.put("spbill_create_ip", ip);//支付ip
        map.put("out_trade_no", orderSn);//商品订单号
        map.put("total_fee", String.valueOf((int)(double)(examination.getPrice() * 100)));//真实金额
        if("2".equals(payType)){
            map.put("trade_type", "NATIVE");//JSAPI、h5调用
        }else {
            map.put("trade_type", "JSAPI");//JSAPI、h5调用
        }
        map.put("openid", user.getOpenid());//支付用户openid

        String sign = OrderUtil.createSign("UTF-8",map);

        map.put("sign",sign);
        String xml=OrderUtil.getRequestXml(map);



        WXPayLog.info("发送给微信的报文：" + xml);
        WXPayLog.info("加密后的的签名字符串：" + sign);

        String res = "";
        try {
            res = invoker(xml,"https://api.mch.weixin.qq.com/pay/unifiedorder",null);
            res=new String(res.getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e) {
            //TODO
            return;
        }
        WXPayLog.info("请求/pay/unifiedorder下单接口后返回数据：" + res);
        Map<String, String>  WXResult;
        try {
            WXResult=XMLUtil.doXMLParse(res);
        }catch (JDOMException e) {
            WXPayLog.info("解析微信方返回时报错"+e);
            result.put("ret_code", 0);
            result.put("ret_msg", "解析微信方返回时报错"+e);
            ResultUtil.writeResult(response, result.toString());
            return ;
        }
        if ("SUCCESS".equals(WXResult.get("return_code"))){
            String payId=WXResult.get("prepay_id");
            result.put("ret_code", 1);
            result.put("ret_msg", res);
            result.put("prepay_id",payId);
            UserExamination userExamination=UserExamination.createOrder(user.getId(), examination.getId(),examination.getName(), orderSn,payId);
            if("2".equals(payType)){
                result.put("code_url", WXResult.get("code_url"));//JSAPI、h5调用
                userExamination.setCodeUrl(WXResult.get("code_url"));
                userExamination.setPayType(1);
            }else {
                userExamination.setPayType(0);
            }
            userExaminationService.insert(userExamination);
        }else {
            result.put("ret_code", 0);
            result.put("ret_msg", "调用微信支付功能失败！");
        }
        ResultUtil.writeResult(response, result.toString());
    }

    /**
     * 第四步，确认支付
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(method = RequestMethod.POST,params = "action=payCheck")
    public void payCheck(HttpServletRequest request,HttpServletResponse response) throws IOException, InterruptedException {
        JSONObject result=new JSONObject();
        String prepayId=request.getParameter("prepayId");
        WXPayLog.info("开始验证支付信息，预付款id为："+prepayId);
        LocalTime limitTime=LocalTime.now().plusSeconds(5);
        while (LocalTime.now().compareTo(limitTime)<0){
            Thread.sleep(100);
            Map<String,Object> param=new HashMap<>();
            param.put("prepayId",prepayId);
            List<UserExamination> userExaminationEntitys=userExaminationService.findEntitys(param);
            if (userExaminationEntitys.isEmpty()){
                WXPayLog.info("支付确认失败，原因为prePayId不存在"+prepayId);
                result.put("ret_code",1);
                result.put("ret_msg","支付确认失败，原因为prePayId不存在");
                ResultUtil.writeResult(response,result.toString());
                return;
            }else {
                UserExamination userExamination=userExaminationEntitys.get(0);
                switch (userExamination.getHasPayed()){
                    case 0:
                        continue;
                    case 1:
                        WXPayLog.info("支付确认通过,prepayId为"+prepayId);
                        result.put("ret_code",0);
                        result.put("ret_msg","支付成功");
                        result.put("examinationId",userExamination.getExaminationId());
                        ResultUtil.writeResult(response,result.toString());
                        return;
                    case -1:
                        WXPayLog.info("支付确认失败,prepayId为"+prepayId);
                        result.put("ret_code",1);
                        result.put("ret_msg","支付失败");
                        ResultUtil.writeResult(response,result.toString());
                        return;
                    default:
                        WXPayLog.info("异常的支付状态,prepayId为"+prepayId);
                        continue;
                }
            }
        }
        WXPayLog.info("支付确认超时！"+prepayId);
        result.put("ret_code",1);
        result.put("ret_msg","支付确认超时");
        ResultUtil.writeResult(response,result.toString());
    }

    @RequestMapping(method = RequestMethod.POST,params = "action=clearPay")
    public void clearPay(HttpServletRequest request,HttpServletResponse response) throws IOException, InterruptedException {
        JSONObject result=new JSONObject();
        String examinationId=request.getParameter("examinationId");
        User user= (User) request.getSession().getAttribute(ConstantsUtil.ADMINUSER);
        Map<String,Object> param=new HashMap<>();
        param.put("userId",user.getId());
        param.put("examinationId",examinationId);

        userExaminationService.findEntitys(param).forEach(i -> userExaminationService.deleteByKey(i.getId().toString()));
        HttpSession session=request.getSession();
        session.removeAttribute("remainTime");
        session.removeAttribute("startExamTime");
        session.removeAttribute("examinationQuestionMap");
        session.removeAttribute("examinationAnswerMap");

        result.put("ret_code", 1);
        result.put("ret_msg","删除成功");
        ResultUtil.writeResult(response, result.toString());

    }
}
