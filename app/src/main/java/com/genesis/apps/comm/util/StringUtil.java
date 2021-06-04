package com.genesis.apps.comm.util;

import android.text.TextUtils;

import com.google.re2j.Pattern;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {

    /**
     * @biref decimal separator
     * 숫자를 콤마로 구분
     *
     * @param value 구분하려는 값
     * @return 콤마로 구분된 값
     */
    public static String getDigitGrouping(int value) {
        return NumberFormat.getInstance(Locale.getDefault()).format(value);
    }

    public static String getDigitGrouping(double value) {
        return NumberFormat.getInstance(Locale.getDefault()).format(value);
    }

    public static String getDigitGroupingString(String value) {
        long valueLong=0;
        try{
            valueLong = Long.parseLong(value);
        }catch (Exception e){
            valueLong = 0;
        }

        return NumberFormat.getInstance(Locale.getDefault()).format(valueLong);
    }

    //숫자를 받아서 10,000원 형태를 반환
    public static String getPriceString(int value) {
        return getPriceString(""+value);
    }

    //숫자만 써있는 문자열을 받아서 10,000원 형태를 반환
    public static String getPriceString(String value) {
        return getDigitGroupingString(value) + "원";
    }

    //숫자를 받아서 -10,000원 형태를 반환 (입력은 양수, 리턴은 음수 기호 붙음)
    public static String getDiscountString(int value) {
        return getDiscountString(""+value);
    }

    //숫자만 써있는 문자열을 받아서 -10,000원 형태를 반환 (입력은 양수, 리턴은 음수 기호 붙음)
    public static String getDiscountString(String value) {
        return "-" + getDigitGroupingString(value) + "원";
    }

    /**
     * @brief 폰번호 포맷 변경
     * 폰번호가 +82로 시작되거나 대쉬가 있는 경우 아래 포맷으로 변경
     * 01000000000
     * @param phone
     * @return
     */
    public static String parsingPhoneNumber(String phone){

        if(!TextUtils.isEmpty(phone)){
            if (phone.startsWith("+82")) {
                phone = phone.replace("+82", "0");
            }
            phone = phone.replaceAll("-", "");
        }

        return phone;
    }

    /**
     * @brief 자리수 채우기
     * yyyyMMddHHmmss보다 작은 텍스트 값일 경우
     * 나머지를 0으로 채움
     * @param date
     * @return
     */
    public static String fillDateFormatyyyyMMddHHmmss(String date){
        return String.format("%-14s", date).replaceAll(" ","0");
    }


    public static int isValidInteger(String value){
        int retv = 0;
        try{
            retv = Integer.parseInt(value);
        }catch (Exception e){
            retv = 0;
        }
        return retv;
    }

    public static String isValidString(String value){
        String retv = "";
        try {
            retv = (TextUtils.isEmpty(value)||value.equalsIgnoreCase("null")) ? "" : value;
        } catch (Exception e) {
            retv = "";
        }
        return retv;
    }

    /**
     * @brief 폰 번호 마스킹
     * 0101111111 or 010-1111-1111 구조를 아래와 같이 마스킹 처리
     * 010-xxxx-1111 (가운데 숫자는 3자리 예외처리 됨)
     *
     * @param input 마스킹 처리할 폰번호
     * @return 마스킹 처리된 번호
     */
    public static String getPhoneMask(String input){
        if(!TextUtils.isEmpty(input)){
            return Pattern.compile(StringRe2j.PHONE_ORIGINAL).matcher(input).replaceAll(StringRe2j.PHONE_MASK);
        }else{
            return input;
        }
    }

    /**
     * @brief 이름 마스킹
     * 아래 정책으로 마스킹 처리
     * 김가나다라 —> 김***라
     * 김가나다 —> 김**다
     * 김경희 —> 김*희
     * 김경 —> 김*
     * 김 - > *
     * @param name 마스킹 처리할 이름
     * @return 마스킹된 이름
     */
    public static String getNameMask(String name){
        if (TextUtils.isEmpty(name))
            return "";

        StringBuffer stringBuffer = new StringBuffer(name);
        final int nameLength = name.length();

        switch (nameLength){
            case 1:
            case 2:
                //성+이름이 2글자 이내일 경우에는 마지막 글자를 마스킹 처리
                stringBuffer.setCharAt(nameLength-1, '*');
                break;
            default:
                //성+이름이 3글자 이상인 경우 첫글자와 마지막글자를 제외한 나머지를 마스킹 처리
                for(int target=1; target<nameLength-1; target++){
                    stringBuffer.setCharAt(target, '*');
                }
                break;
        }

        return stringBuffer.toString();
    }
}
