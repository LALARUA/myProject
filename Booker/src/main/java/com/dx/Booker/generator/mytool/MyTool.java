package com.dx.Booker.generator.mytool;

import java.util.regex.Pattern;

public class MyTool {
    public static boolean isInteger(String str){
            Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
            return pattern.matcher(str).matches();
    }
    public static Long toInteger(String str){
        return new Long(str);
    }
}
