package example.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


import example.util.ConstantsUtil;
import example.util.StringUtil;
import org.apache.log4j.Logger;


/**
 * @author John Lee
 */
public class AdminsecurityFilter implements Filter {

    private static final Logger logger = Logger
            .getLogger(AdminsecurityFilter.class);

    
    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        response.setContentType("text/html; charset=UTF-8");
        try {
            HttpServletRequest req = (HttpServletRequest) request;
            Object object = req.getSession().getAttribute(ConstantsUtil.ADMINUSER);
            String action = req.getParameter("action");
            logger.info(req.getRequestURI() + "?action=" + action);
            if (object != null) { // 获取用户信息 这一步应该在登录成功后操作

                chain.doFilter(request, response);
                return;
            }

            String path = httpServletRequest.getRequestURI();

            path = path.toLowerCase();
            // 忽略login.htm、静态js、css图片等
            if (path.endsWith("login.htm")) {
                chain.doFilter(request, response);
                return;
            } else {
                req.getSession().setAttribute("url",path);
                String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ConstantsUtil.APPID+"&redirect_uri=http%3a%2f%2fsfkj.hzne.cn%2flogin.htm&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
                this.go(response.getWriter(), url);
                return;
            }
        } catch (Exception e) {
            logger.error(e);
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {

    }

    private void go(PrintWriter pw, String url) {
        StringBuffer sb = new StringBuffer();
        sb.append("<script type='text/javascript'>parent.parent.window.location.href='");
        sb.append(url);
        sb.append("';</script>");
        pw.println(sb);

        pw.flush();
        pw.close();
    }

    public static void main(String[] args) {
        System.out.println(URLDecoder.decode("http%3a%2f%2fsfkj.hzne.cn%2flogin.htm"));
    }
}
