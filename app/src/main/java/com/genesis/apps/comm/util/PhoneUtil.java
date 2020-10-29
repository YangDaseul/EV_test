package com.genesis.apps.comm.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil {

    //지정된 번호로 즉시 전화 걸기 :
    // TODO : 추가 권한 필요. <uses-permission android:name="android.permission.CALL_PHONE"/>
    //todo
    // 코드는 일단 쓰는김에 넣어뒀는데 지금은 권한 때문에 사용 못 함.
    // 호출 못 하게 private으로 해둠.
    // 관련 정책 등 확인되면 권한 검사 등 해결하고 public으로 해금하기
    private static void phoneCall(Activity activity, String phoneNumber) {
        activity.startActivity(getCallIntent(makePhoneNumberUri(phoneNumber)));
    }

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
