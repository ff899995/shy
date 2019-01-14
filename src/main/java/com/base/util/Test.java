package com.base.util;

import com.alibaba.fastjson.JSONObject;
import lombok.val;
import org.springframework.util.StringUtils;

import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author fanl
 * @date 2018/12/4 15:19
 */
public class Test{

    public static void main(String[] args) {
        String str = "范磊";

        if (BaseUtils.checkName(str)){
            System.out.println("合法");
        }

    }

    public static Object DataPointValue(String val) {
        if (val.equals("true")) {
            return true;
        } else if (val.equals("false")) {
            return false;
        } else if (StringUtils.isEmpty(val)){
            return val;
        } else if (isInteger(val)) {
            return Integer.parseInt(val);
        }
        return val;
    }

    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }



}