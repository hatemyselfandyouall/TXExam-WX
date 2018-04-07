package example.controller.examController;

/**
 * 付款回退页面，必须在这里做一个处理
 * Created by YS-GZD-1495 on 2018/2/10.
 */

import example.model.dataobject.UserExamination;
import example.model.service.UserExamService;
import example.model.service.UserExaminationService;
import example.util.*;
import example.util.logger.WXPayLog;
import org.apache.http.client.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.logging.Logger;

@Controller
@RequestMapping("/payBack.htm")
public class PayBackCheckController {

    @Autowired
    UserExaminationService userExamService;

    /**
     * 第二部js支付
     *
     * @param request
     * @param response
     * @param map
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public void index(HttpServletRequest request, HttpServletResponse response, ModelMap map) throws Exception {
        //读取参数
        InputStream inputStream;
        StringBuffer sb = new StringBuffer();
        inputStream = request.getInputStream();
        String s;
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((s = in.readLine()) != null) {
            sb.append(s);
        }
        in.close();
        inputStream.close();

        //解析xml成map
        Map<String, String> m = new HashMap<String, String>();
        m = XMLUtil.doXMLParse(sb.toString());

        //过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);

            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();

            }
            packageParams.put(parameter, v);
        }

        // 账号信息
        String key = ConstantsUtil.PARTNER_KEY; // key

        WXPayLog.info(packageParams);
        //判断签名是否正确
        if (WXPayUtil.isTenpaySign("UTF-8", packageParams, key)) {
            //------------------------------
            //处理业务开始
            //------------------------------
            String resXml = "";
            if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
                // 这里是支付成功
                //////////执行自己的业务逻辑////////////////
                String mch_id = (String) packageParams.get("mch_id");
                String openid = (String) packageParams.get("openid");
                String is_subscribe = (String) packageParams.get("is_subscribe");
                String outTradeNo = (String) packageParams.get("out_trade_no");

                String total_fee = (String) packageParams.get("total_fee");
                String transactionId=(String) packageParams.get("transaction_id");

                Map<String,Object> param=new HashMap<>();
                param.put("orderId",outTradeNo);

                List<UserExamination> userExaminations=userExamService.findEntitys(param);
                WXPayLog.info("支付成功，根据数据查找订单"+param);
                if (userExaminations.isEmpty()){
                    WXPayLog.info("支付失败:不存在此订单"+outTradeNo);
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                    BufferedOutputStream out = new BufferedOutputStream(
                            response.getOutputStream());
                    out.write(resXml.getBytes());
                    out.flush();
                    out.close();
                    return;
                }else {
                    UserExamination userExamination=userExaminations.get(0);
                    userExamination.setPayTime(new Date());
                    userExamination.setHasPayed(1);
                    userExamination.setUpdateTime(new Date());
                    userExamService.update(userExamination);
                }

                //////////执行自己的业务逻辑////////////////

                WXPayLog.info("支付成功");
                //通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
                resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                        + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

            } else {
                WXPayLog.info("支付失败,错误信息：" + packageParams.get("err_code"));
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                        + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
            }
            //------------------------------
            //处理业务完毕
            //------------------------------
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } else {
            WXPayLog.info("通知签名验证失败");
        }
    }
}
