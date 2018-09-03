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
    public static String handleBookAuthor(String author){
        int length = author.length();
        char[] chars = author.toCharArray();
        if (chars[0]=='['&&chars[1]=='"'&&chars[length-1]==']'&&chars[length-2]=='"')
            return author.substring(2,length-2);
        else return author;
    }
}
