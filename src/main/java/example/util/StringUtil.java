package example.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import sun.security.pkcs11.wrapper.Constants;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {

    private final static Logger LOG = Logger.getLogger(StringUtil.class);



    public static String getPhoneNumber(String phone) {
        String phoneNumber = phone.substring(0, 3) + "****" + phone.substring(7);
        return phoneNumber;
    }

    public static Integer getValueFromRequest(HttpServletRequest request, String key, Integer defaultValue) {
        Integer value = defaultValue;
        try {
            value = Integer.parseInt(request.getParameter(key));
        } catch (NumberFormatException e) {
        }
        return value;
    }

    public static String parseDiagnosis(String dia, int num) {
        if (!StringUtils.isNotBlank(dia)) {
            return "";
        }
        dia = dia.replace("\n", " ");
        dia = dia.trim();
        if (dia.length() > num) {
            return dia.substring(0, num) + "...";
        } else {
            if (dia.length() > 50) {
                return dia.substring(0, 50) + "...";
            }
            return dia.substring(0, dia.length()) + "";
        }
    }

    /**
     * 截取指定字节长度的字符串，不能返回半个汉字
     * 例如：
     * 如果网页最多能显示17个汉字，那么 length 则为 34
     * StringTool.getSubString(str, 34);
     *
     * @param str
     * @param length
     * @return
     */
    public static String getSubString(String str, int length) {
        int count = 0;
        int offset = 0;
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] > 256) {
                offset = 2;
                count += 2;
            } else {
                offset = 1;
                count++;
            }
            if (count == length) {
                return str.substring(0, i + 1);
            }
            if ((count == length + 1 && offset == 2)) {
                return str.substring(0, i);
            }
        }
        return "";
    }


    /**
     * 根据手机号码判断运营商 0为移动，1为联通，2为电信 (如要判断号码请补充方法中的号码段数组)
     *
     * @param phone
     * @return
     */
    public static Integer phoneType(String phone) {
        Integer t = 0;
        if (phone.startsWith("0") || phone.startsWith("+860")) {
            phone = phone.substring(phone.indexOf("0") + 1, phone.length());
        }
        List<String> chinaUnicom = Arrays.asList("130", "131",
                "132", "155", "156", "185", "186");
        List<String> chinaMobile = Arrays.asList("134", "135",
                "136", "137", "138", "139", "147", "150", "151", "152", "157",
                "158", "159", "182", "187", "188");
        List<String> chinaTele = Arrays.asList("133", "153",
                "180", "181", "189");
        boolean bolChinaUnicom = (chinaUnicom.contains(phone.substring(0, 3)));
        boolean bolChinaMobile = (chinaMobile.contains(phone.substring(0, 3)));
        boolean bolChinaTele = (chinaTele.contains(phone.substring(0, 3)));
        if (bolChinaUnicom) {
            t = 1;
        } else if (bolChinaMobile) {
            t = 0;
        } else if (bolChinaTele) {
            t = 2;
        }
        return t;
    }

    public static boolean isNotBlank(String s) {
        return !isBlank(s);
    }

    public static boolean isBlank(String s) {
        return StringUtils.isBlank(s) || "null".equalsIgnoreCase(s)
                || "(null)".equalsIgnoreCase(s);
    }

    /**
     * 转码
     */
    public static String strCode(String str) {
        if (StringUtil.isNotBlank(str)) {
            try {
                str = new String(str.getBytes("iso-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                LOG.error(e.getMessage(), e);
            }
        }
        return str;
    }

    public static boolean equal(String a, String b) {
        if (a == null) {
            return false;
        } else {
            return a.equals(b);
        }
    }

    /**
     * 反JS注入语句
     */
    public static String JSInjectionTrans(String input){
        return input.replaceAll("<","&lt;").replaceAll(">","&gt");
    }

    /**
     * 获取编码
     * @param str
     * @return
     */
    public static String getEncoding(String str) {
        String encode = "GB2312";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
                String s = encode;
                return s;      //是的话，返回“GB2312“，以下代码同理
            }
        } catch (Exception exception) {
        }
        encode = "ISO-8859-1";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
                String s1 = encode;
                return s1;
            }
        } catch (Exception exception1) {
        }
        encode = "UTF-8";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
                String s2 = encode;
                return s2;
            }
        } catch (Exception exception2) {
        }
        encode = "GBK";
        try {
            if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
                String s3 = encode;
                return s3;
            }
        } catch (Exception exception3) {
        }
        return "";        //如果都不是，说明输入的内容不属于常见的编码格式。
    }
    public static int occurTimes(String string, char a) {
       int count=0;
        for (int i=0;i<string.length();i++){
            if (string.charAt(i)==a){
                count++;
            }
        }
        return count;
    }

    public static String  soutByte(byte[] bytes){
        String result="";
        for (int i=0;i<bytes.length;i++){
            result+=bytes[i];
        }
        return result;
    }

    public static byte[] addByte(byte[] data1,byte[] data2){
        byte[] data3 = new byte[data1.length+data2.length];
        System.arraycopy(data1,0,data3,0,data1.length);
        System.arraycopy(data2,0,data3,data1.length,data2.length);
        return data3;
    }
    public static String replaceBlank(String str) {

        String dest = "";

        if (str!=null) {

            Pattern p = Pattern.compile("\\s*|\t|\r|\n");

            Matcher m = p.matcher(str);

            dest = m.replaceAll("");

        }

        return dest;

    }



    /*
  * 转义字符替换
  * */
    public static String replaceStr(String str) {
        String result = "";
        result = str.replace("u002e", ".").replace("u0024", "$").replace("u005e", "^").replace("u007b", "{").replace("u005b", "[")
                .replace("u0028", "(").replace("u007c", "|").replace("u0029", ")").replace("u002a", "*").replace("u002b", "+").replace("u003f", "?")
                .replace("u005c", "\\").replace("u003a", ":").replace("u007d", "}").replace("u005d", "]").replace("u002c", ",").replace("u0027", "'")
                .replace("u0022", "\"").replace("u002d", "-").replaceAll("u001a", "&").replaceAll("u001b","/");
        return result;
    }
    /*
      * 转义字符替换
      * */
    public static String stringTrans(String str) {
        String result = "";
        result = str.replace(".", "u002e").replace("$", "u0024").replace("^", "u005e").replace("{", "u007b").replace("[", "u005b")
                .replace("(", "u0028").replace("|", "u007c").replace(")", "u0029").replace("*", "u002a").replace("+", "u002b").replace("?", "u003f")
                .replace("\\", "u005c").replace(":", "u003a").replace("}", "u007d").replace("]", "u005d").replace(",", "u002c").replace("'", "u0027")
                .replace("\"", "u0022").replace("-", "u002d").replaceAll("&", "u001a").replaceAll("/", "u001b");
        return result;
    }

    /**
     *必要的转化
     * @param args
     */
    public static String examAnswer2questionAnswer(String answer){
        if (StringUtil.isBlank(answer)){
            return "";
        }
        String result="";
        String[] choice=answer.split(",");
        for (int j=0;j<choice.length;j++){
            if(!StringUtil.isBlank(choice[j])){
                result+=choice[j];
                result+="|";
            }
        }
        result=result.substring(0,result.length()-1);
        return result;
    }


    public static void main(String[] args) {
        String t1="";
        String t2="";
        System.out.println(new String(addByte(t1.getBytes(),t2.getBytes())));
    }
}
