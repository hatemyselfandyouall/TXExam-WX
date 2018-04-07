package example.controller.examController;

import example.controller.BasicController;
import example.controller.ReportCrawlerPorxy;
import example.util.ConstantsUtil;
import example.util.OrderUtil;
import example.util.WXPayUtil;
import example.util.XMLUtil;
import example.util.logger.WXPayLog;
import net.sf.json.JSONObject;
import org.jdom.JDOMException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Controller
@RequestMapping("/home/weixin")
public class JSJDKController extends ReportCrawlerPorxy{
    @RequestMapping(value = "/share", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> share(HttpServletRequest request) {
//        String urlTemp = "http://" + request.getServerName() + request.getContextPath();
//        String urlpath = "http://" + request.getServerName();
//        String appUrl = request.getParameter("url");
//        if (request.getParameter("code") != null) {
//            appUrl += "&code=" + request.getParameter("code");
//        }
//        if (request.getParameter("state") != null) {
//            appUrl += "&state=" + request.getParameter("state");
//        }
//        JSONObject result=new JSONObject();
        Map<String, String[]> params = request.getParameterMap();
        String appUrl = "";
        for (String key : params.keySet()) {
            String[] values = params.get(key);
            for (int i = 0; i < values.length; i++) {
                String value = values[i];
                appUrl += key + "=" + value + "&";
            }
        }
        appUrl=appUrl.substring(4,appUrl.length()-1);
        SortedMap<String, String> map = new ConcurrentSkipListMap<>() ;
        map.put("noncestr", WXPayUtil.generateUUID());//随机字符串
        map.put("timestamp", ((Long)WXPayUtil.getCurrentTimestamp()).toString());//异步回调api
        map.put("jsapi_ticket", WXPayUtil.getTicket());//支付ip
        map.put("url",appUrl);
        String sign = OrderUtil.createSignForJSJDK("UTF-8",map);
        map.put("signature",sign);
        map.put("appId", ConstantsUtil.APPID);//公众号id
        WXPayLog.info("获取jsjdk权限"+map);
        return map;
    }
}
