package com.genesis.apps.comm.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil {
    //전화 액티비티 호출 : 지정된 번호가 입력된 상태
    public static void phoneDial(Activity activity, String phoneNumber) {
        activity.startActivity(getDialIntent(makePhoneNumberUri(phoneNumber)));
    }

    private static String makePhoneNumberUri(String phoneNumber) {
        return "tel:" + phoneNumber;
    }

    private static Intent getCallIntent(String phoneNumberUri) {
        return new Intent("android.intent.action.CALL", Uri.parse(phoneNumberUri));
    }

    private static Intent getDialIntent(String phoneNumberUri) {
        return new Intent("android.intent.action.DIAL", Uri.parse(phoneNumberUri));
    }

}
