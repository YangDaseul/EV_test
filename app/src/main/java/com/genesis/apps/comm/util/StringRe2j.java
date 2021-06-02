package com.genesis.apps.comm.util;

import android.text.TextUtils;

import com.google.re2j.Pattern;

public class StringRe2j {

    private static final String PHONE_ORIGINAL = "^(\\\\d{3})-?(\\\\d{3,4})-?(\\\\d{4})$";
    private static final String PHONE_MASK = "$1-****-$3";
    private static final String NAME_ORIGINAL = "(?<=.{1}.";
    private static final String NAME_MASK = "*";

    private StringRe2j() {
        throw new IllegalStateException("Utility class");
    }

    public static String replaceFirst(String input, String regex, String replacement) {
        return Pattern.compile(regex).matcher(input).replaceFirst(replacement);
    }

    public static String replaceAll(String input, String regex, String replacement) {
        return Pattern.compile(regex).matcher(input).replaceAll(replacement);
    }

    public static boolean matches(String input, String regex) {
        return Pattern.matches(regex, input);
    }

//    public static String getNameMask(String input) {
//        if(!TextUtils.isEmpty(input)&&matches(input,PHONE_ORIGINAL)){
//            return Pattern.compile(PHONE_ORIGINAL).matcher(input).replaceAll(PHONE_MASK);
//        }else{
//            return input;
//        }
//    }

    public static String getPhoneMask(String input){
        if(!TextUtils.isEmpty(input)){
            return Pattern.compile(PHONE_ORIGINAL).matcher(input).replaceAll(PHONE_MASK);
        }else{
            return input;
        }
    }
    public static String getNameMask(String name){

        String maskName="";

        if(!TextUtils.isEmpty(name)){
            name = name.trim();
            maskName = name;
            if (maskName.length() == 3) {
                maskName = maskName.substring(0, 1) + "*" + maskName.substring(2, 3);
            } else if (maskName.length() > 1) {
                maskName = name.substring(0, 1);
                for (int n = 0; n < maskName.length() - 1; n++) {
                    maskName += "*";
                }
            }
        }

        if(TextUtils.isEmpty(maskName))
            maskName = name;

        return maskName;
    }


    public static String getMaskName(String name) {
        String maskedName = "";     // 마스킹 이름
        String firstName = "";      // 성
        String middleName = "";     // 이름 중간
        String lastName = "";       //이름 끝
        int lastNameStartPoint;     // 이름 시작 포인터

        if(!TextUtils.isEmpty(name)){
            name = name.trim();
            if(name.length() > 1){
                firstName = name.substring(0, 1);
                lastNameStartPoint = name.indexOf(firstName);

                if(name.length() > 2){
                    //3글자 이상인 경우
                    middleName = name.substring(lastNameStartPoint + 1, name.length()-1);
                    lastName = name.substring(lastNameStartPoint + (name.length() - 1), name.length());
                }else{
                    middleName = name.substring(lastNameStartPoint + 1, name.length());
                }

                String makers = "";
                for(int i = 0; i < middleName.length(); i++){
                    makers += "*";
                }

                lastName = middleName.replace(middleName, makers) + lastName;
                maskedName = firstName + lastName;
            }else{
                maskedName = name;
            }
        }

        return maskedName;
    }
}
