package example.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    private final static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
    private final static SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat week = new SimpleDateFormat("EE");
    private final static SimpleDateFormat yyyymmddee = new SimpleDateFormat("yyyy-MM-dd EE");
    private final static SimpleDateFormat yyyyMMddHHmm = new SimpleDateFormat("yyyyMMddHHmm");

    private final static SimpleDateFormat HHmm = new SimpleDateFormat("HHmm");

    public final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public final static SimpleDateFormat sdf3 = new SimpleDateFormat("HHmm");

    private final static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    private final static SimpleDateFormat yyyyMMddhhmmssSSS = new SimpleDateFormat("yyyyMMddhhmmssSSS");
    public final static SimpleDateFormat sdf5 = new SimpleDateFormat("yyyy年MM月");
    private final static SimpleDateFormat sdf6 = new SimpleDateFormat("yyyy-M-d");
    public final static SimpleDateFormat sdf7 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    private final static SimpleDateFormat sdf15 = new SimpleDateFormat("HHmm");


    public String simpleDate(Date date) {
        if (date == null) {
            return "";
        }
        return sdf.format(date);
    }

    public static String simpleDate1(String date1) {
        if (date1 == null) {
            return "";
        }
        return sdf.format(date1);
    }

    public static String simpleDate2(Date date) {
        if (yyyyMMddhhmmssSSS == null) {
            return null;
        }
        return yyyyMMddhhmmssSSS.format(date);

    }

    /**
     * 取当前时间
     *
     * @return
     */
    public static String getCurrentTime() {

        Calendar calendar = Calendar.getInstance();

        int i = calendar.get(1);
        int j = calendar.get(2) + 1;
        int k = calendar.get(5);
        return "" + i + (j >= 10 ? "" + j : "0" + j) + (k >= 10 ? "" + k : "0" + k);
    }

    public static Date calculateDate(Date startDay, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDay);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return cal.getTime();
    }

    public static Date calculateMonth(Date startDay, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(startDay);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    public static String getAddMonth(int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.add(field, amount);
        return DateUtil.getyyyy_MM_dd(cal.getTime());
    }

    /**
     * 获得yyyy-MM形式的月份日期
     *
     * @return
     */
    public static String getyyyy_MM(Date date) {
        try {
            return dateToString1(date).substring(0, 7);
        } catch (Exception e) {
            return dateToString1(new Date()).substring(0, 7);
        }
    }

    /**
     * String 获取当前时间yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return dateToString4(getCurrentDate());
    }

    public static String getCurrentDateddHH() {
        return dateToString11(getCurrentDate());
    }

    public static String dateToString11(Date date) {
        try {
            return HHmm.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    /**
     * String 获取今天日期yyyyMMdd
     *
     * @return
     */
    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        return DateUtil.getyyyyMMdd(calendar.getTime());
    }

    /**
     * String 获取今天日期yyyy-MM-dd
     *
     * @return
     */
    public static String getToday2() {
        Calendar calendar = Calendar.getInstance();
        return DateUtil.getyyyy_MM_dd(calendar.getTime());
    }

    /**
     * String 获取今天日期week
     *
     * @return
     */
    public static String getWeek() {
        Calendar calendar = Calendar.getInstance();
        return DateUtil.getWeek(calendar.getTime());
    }

    /**
     * String 获取明天日期yyyyMMdd
     *
     * @return
     */
    public static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return DateUtil.getyyyyMMdd(calendar.getTime());
    }

    /**
     * String 获取几天后日期yyyyMMdd
     *
     * @return
     */
    public static String getDateByDays(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return DateUtil.getyyyyMMdd(calendar.getTime());
    }

    /**
     * String 获取几天后日期yyyy-MM-dd
     *
     * @return
     */
    public static String getDateByDays2(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return DateUtil.getyyyy_MM_dd(calendar.getTime());
    }

    /**
     * 将日期格式化为字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 字符串 转 日期
     *
     * @param time
     * @param pattern
     * @return
     */
    public static Date stringToDate(String time, SimpleDateFormat sdf) {
        Date date = null;
        if (time == null) {
            return date;
        }
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getWeek(Date date) {
        if (date == null) {
            return "";
        }
        return week.format(date);
    }

    public static String getyyyyMMdd(Date date) {
        if (date == null) {
            return "";
        }
        return yyyyMMdd.format(date);
    }

    public static String getyyyy_MM_dd(Date date) {
        if (date == null) {
            return "";
        }
        return yyyy_MM_dd.format(date);
    }

    public static String getyyyymmddee(Date date) {
        if (date == null) {
            return "";
        }
        return yyyymmddee.format(date);
    }

    public static Date getyyyy_MM_dd(String date) {
        try {
            return yyyy_MM_dd.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * String 获取时间 yyyy-MM-dd HH:mm
     *
     * @param date
     * @return
     */
    public static String format1(Date date) {
        if (date == null) {
            date = new Date();
        }
        return sdf1.format(date);
    }

    public static String dateToString1(Date date) {
        try {
            return sdf1.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * Date 获取时间 yyyy-MM-dd HH:mm
     *
     * @param String
     * @return Date
     */
    public static Date StringToDate1(String date) {
        try {
            return sdf1.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * Date 获取时间 yyyy/MM/dd
     *
     * @param String
     * @return Date
     */
    public static Date StringToDate5(String date) {
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * Date 获取时间yyyyMMddHHmm
     *
     * @param String yyyyMMddHHmm
     * @return Date
     */
    public static Date StringToDate2(String date) {
        try {
            return yyyyMMddHHmm.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    /**
     * Date 获取时间yyyyMMdd
     *
     * @param String yyyyMMdd
     * @return Date
     */
    public static Date StringToDate3(String date) {
        try {
            return yyyyMMdd.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    public static Date StringToDate4(String date) {
        try {
            return sdf2.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    public static String dateToString4(Date date) {
        try {
            return sdf2.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getHHmm(Date date) {
        try {
            return sdf3.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    public static Integer getWeekId(String date) {
        Date d = new Date();
        try {
            d = yyyyMMdd.parse(date);
        } catch (ParseException e) {
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        Integer i = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        return i;
    }

    /**
     * 获取周几
     *
     * @param date yyyyMMdd
     * @return
     */
    public static String getWeek(String date) {
        String week = "";
        switch (getWeekId(date)) {
            case 1:
                week = "周一";
                break;
            case 2:
                week = "周二";
                break;
            case 3:
                week = "周三";
                break;
            case 4:
                week = "周四";
                break;
            case 5:
                week = "周五";
                break;
            case 6:
                week = "周六";
                break;
            case 0:
                week = "周日";
                break;

            default:
                break;
        }
        return week;
    }

    /**
     * 时间计算（日）
     *
     * @param date
     * @param n
     * @return
     */
    public static Date getDate(Date date, int n) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(Calendar.DAY_OF_MONTH, n);
        return gc.getTime();
    }

    public static Date getDate(Date date, int field, int n) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.add(field, n);
        return gc.getTime();
    }

    public static void main2(String[] args) {

        System.out.print(DateUtil.dateToString1(DateUtil.StringToDate2("20130314" + "2359")));

    }

    public static String stringToDate15(Date date) {
        if (date == null) {
            return "";
        }
        return sdf15.format(date);

    }

    public static String getyyyy_MM_dd2(String date) throws ParseException {
        if (date == null) {
            return "";
        }
        return yyyy_MM_dd.format(yyyy_MM_dd.parse(date));
    }

    public static JSONArray getCreateTime() {
        String ymd = "yyyy-MM-dd HH:00:00";
        JSONArray ary = new JSONArray();
        ary.add(getTime(Calendar.HOUR, -2, ymd, "2小时之内"));
        ary.add(getTime(Calendar.HOUR, -12, ymd, "12小时之内"));
        ary.add(getTime(Calendar.DATE, -1, "一天之内"));
        ary.add(getTime(Calendar.DATE, -3, "三天之内"));
        return ary;
    }

    public static JSONArray getApportionTime() {
        JSONArray ary = new JSONArray();
        ary.add(getTime(Calendar.DATE, -1, "一天之内"));
        ary.add(getTime(Calendar.DATE, -3, "三天之内"));
        ary.add(getTime(Calendar.DATE, -7, "一周之内"));
        return ary;
    }

    public static JSONObject getTime(int field, int amount, String name) {
        Calendar time = Calendar.getInstance();
        time.add(field, amount);
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("time", dateToString(time.getTime(), "yyyy-MM-dd"));
        return obj;
    }

    public static JSONObject getTime(int field, int amount, String ymd, String name) {
        Calendar time = Calendar.getInstance();
        time.add(field, amount);
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("time", dateToString(time.getTime(), "yyyy-MM-dd"));
        return obj;
    }

    /**
     * 获取本月第一天
     *
     * @param ymd
     * @return
     */
    public static String getMonFirst(String ymd) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DAY_OF_MONTH, 1);
        return dateToString(time.getTime(), ymd);
    }

    /**
     * 获取今年第一天
     *
     * @param ymd
     * @return
     */
    public static String getYearFirst(String ymd) {
        Calendar time = Calendar.getInstance();
        time.set(Calendar.DAY_OF_YEAR, 1);
        return dateToString(time.getTime(), ymd);
    }

    public static String getTime(String ymd) {
        Calendar time = Calendar.getInstance();
        return dateToString(time.getTime(), ymd);
    }

    public static String getTimeMinus(Date start, Date end) {
        long day1 = 0;
        long hour1 = 0;
        long minute1 = 0;
        try {
            long time = end.getTime() - start.getTime();
            long between = time / 1000;//除以1000是为了转换成秒
            day1 = between / (24 * 3600);
            hour1 = between % (24 * 3600) / 3600;
            minute1 = between % 3600 / 60;
            long second1 = between % 60 / 60;
        } catch (Exception e) {
            return null;
        }
        return day1 + "天" + hour1 + "小时" + minute1 + "分";
    }

    public static String toLocalTime(String time) {
        String timeString = null;
        try {
            Date d = sdf2.parse(time);
            timeString = sdf4.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }

    public static String toLocalTime2(String time) {
        String timeString = null;
        try {
            Date d = sdf1.parse(time);
            timeString = sdf4.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;
    }

    public static Date getDateFromsdf4(String time) {
        try {
            Date d = sdf4.parse(time);
            return d;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }



    /**
     * 将yyyy-MM-dd HH:mm:ss"格式的时间转化为yyyy-MM-dd HH:mm"格式
     *
     * @return
     */
    public static String dateChangeNoSecend(String date) {
        try {
            Date temp = sdf2.parse(date);
            String result = sdf1.format(temp);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /***
     * 获取【查询时间】到现在多少天
     *
     * @param fromDate 查询时间
     * @return
     * @throws Exception
     */
    public static int getDayFromCookiePlantTime(Date fromDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);

        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        long interval = now.getTimeInMillis() - cal.getTimeInMillis();
        long syts = interval / 60 / 60 / 1000 / 24;// 获取今天到指定日期剩余天数
        return (int) syts;
    }

    /**
     * 根据Flag获取时间
     *
     * @param flg
     * @return
     */
    public static String getDateFromFlg(String flg) {
        Calendar c = Calendar.getInstance();
        //过去七天
        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        Date week = c.getTime();
        //过去一月
        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date month = c.getTime();
        switch (flg) {
            case "1":
                return yyyy_MM_dd.format(week);
            case "2":
                return yyyy_MM_dd.format(month);
            default:
                return yyyy_MM_dd.format(new Date());
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss"格式的时间转化为xxxx年xx月"格式
     *
     * @return
     */
    public static String dateToNatural(String date) {
        try {
            Date temp = sdf2.parse(date);
            String result = sdf5.format(temp);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /*
    * 将yyyy-MM-dd转为yyyy-M-d
    * */
    public static String toyyyyMd(String date) {
        try {
            Date temp = yyyy_MM_dd.parse(date);
            String result = sdf6.format(temp);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 将时间转化为xxxx年xx月xx日 xx:xx:xx"格式
     *
     * @return
     */
    public static String dateToNatural2(Date date) {
        String result = "";
        if (date != null) {
            result = sdf7.format(date);
        }
        return result;
    }

    /**
     * String 获取*天前日期yyyyMMdd
     *
     * @return
     */
    public static String getDateBeforeToday(Integer days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,-days);
        return DateUtil.getyyyy_MM_dd(calendar.getTime());
    }

    public static Date dateAfter1Day(Date date){
        date.setTime(date.getTime() + 60 * 60 * 24 *1000);
        return date;
    }

    public static Date TwoHourAfter1Day(Date date){
        date.setTime(date.getTime() + 60 * 60 * 2 *1000);
        return date;
    }

    public static void main(String[] args) {
        Date now=new Date();
        System.out.println(dateAfter1Day(now));
    }

    public static String getTimeStamp(){
        Long time=new Date().getTime()/1000;
        return time.toString();
    }
}
