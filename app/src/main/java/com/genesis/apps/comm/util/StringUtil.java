package com.genesis.apps.comm.util;

import android.text.TextUtils;

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

    public static String getDigitGroupingString(String value) {
        return NumberFormat.getInstance(Locale.getDefault()).format(Integer.parseInt(value));
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
            phone = phone.replace("-", "");
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

}
