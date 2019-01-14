package com.base.util;

import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaseUtils {

    // 生成String类型时间
    public static String dateCreate(){
        //获取当前时间，并转换为String
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  //首先设定日期时间格式,HH指使用24小时制,hh是使用12小时制
        Date date = new Date();
        String dateStr = dateFormat.format(date);
        return dateStr;
    }

    // 替代trim()防止出现StringUtils.isEmpty()出现空指针现象
    public static String OverTrim(String str){
        if (StringUtils.isEmpty(str)){
            return str;
        }
        else {
            return str.trim();
        }
    }

    public static boolean TrimIsEmpty(String str){
        if (StringUtils.isEmpty(OverTrim(str))){
            return true;
        } else {
            return false;
        }
    }


    static boolean flag = false;
    static String regex = "";

    public static boolean check(String str, String regex) {
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }
    /**
     * 验证手机号码
     *
     * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、187、188、147
     * 联通号码段:130、131、132、136、185、186、145
     * 电信号码段:133、153、180、189
     *
     * @param cellphone
     * @return
     */
    public static boolean checkCellphone(String cellphone) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    /**
     * 验证用户名
     *
     * @param username
     * @return
     */
    public static boolean checkUsername(String username) {
        String regex = "[a-zA-Z]{1}[a-zA-Z0-9_]{5,15}";
        return check(username, regex);
    }

    /**
     * 验证密码
     *
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        String regex = "[a-zA-Z0-9]{6,16}";
        return check(password, regex);
    }

    /**
     * 验证名称
     *
     * @param name
     * @return
     */
    public static boolean checkName(String name) {
//        String regex = "[\\u4e00-\\u9fa5_a-zA-Z0-9_]{4,10}";
        String regex = "^[\\一-\\龥]{2,6}$";
        return check(name, regex);
    }
}
