package com.example.customview.utils;

public class NumUtils {
    public static Integer parseInt(String s){
        if(s==null || s.isEmpty()) return null;
        if(s.contains("-")){
            return -Integer.parseInt(s.replace("-",""));
        }else{
            return Integer.parseInt(s);
        }
    }
}
