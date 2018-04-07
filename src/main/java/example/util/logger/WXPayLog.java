package example.util.logger;

import org.apache.log4j.Logger;

/**
 * 报告单日志
 * USER: lintc 【lintiancong@zhuojianchina.com】
 * DATE: 2016-12-27 14:06
 */
public class WXPayLog {
    private WXPayLog(){

    }

    private static final Logger RPT_LOG = Logger.getLogger("RPT_LOG");

    public static void info(String title, Object msg) {
        RPT_LOG.info(title + " : " + msg);
    }

    public static void error(String title, Object msg) {
        RPT_LOG.error(title + " : " + msg);
    }

    public static void info(Object msg) {
        RPT_LOG.info(msg);
    }

    public static void error(Object msg) {
        RPT_LOG.error(msg);
    }
}
