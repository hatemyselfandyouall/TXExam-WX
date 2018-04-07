package example.util;

/**
 * Created by YS-GZD-1495 on 2017/12/19.
 * 存放在vm文件中直接调用的方法desu
 */

public class VelocityUtil {
    public static String[] sharpWord(String str,String reg){
        if (StringUtil.isBlank(str)||StringUtil.isBlank(reg)){
            return new String[]{""};
        }
        String [] temp=str.split(reg);
        return temp;
    }
    public static String getChoice(String choice){
        if (StringUtil.isBlank(choice)){
            return  "";
        }
        String result=choice.split("\\.")[0];
        result=result.trim();
        return result;
    }

    public static String getChoiceContent(String choice){
        if (StringUtil.isBlank(choice)){
            return  "";
        }
        String result="";
        String[] results=choice.split("\\.");
        for (int i=1;i<results.length;i++){
            result+=".";
            result+=results[i];
        }
        result=result.substring(1,result.length());
        return result;
    }
    public static String getExamiantionTotalTime(Integer minutes) {
        if (minutes==null) {
            return null;
        }
        if (minutes<60){
            return "00:"+minutes+":00";
        }
        if (minutes>=60){
            return "0"+minutes/60+":"+minutes%60+":00";
        }
        return null;
    }
 }
