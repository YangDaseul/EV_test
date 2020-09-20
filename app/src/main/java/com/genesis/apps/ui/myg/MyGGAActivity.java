package com.genesis.apps.ui.myg;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CompoundButton;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.MYP_0001;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygGaBinding;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;

import java.util.Locale;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MyGGAActivity extends SubActivity<ActivityMygGaBinding> {

    private MYPViewModel mypViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_ga);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        mypViewModel.getRES_MYP_0001().observe(this, new Observer<NetUIResponse<MYP_0001.Response>>() {
            @Override
            public void onChanged(NetUIResponse<MYP_0001.Response> result) {
                String test ="{\n" +
                        "  \"rtCd\": \"0000\",\n" +
                        "  \"rtMsg\": \"Success\",\n" +
                        "  \"mbrStustCd\": \"1000\",\n" +
                        "  \"ccspEmail\": \"test@email.com\",\n" +
                        "  \"mbrNm\": \"댕댕이\",\n" +
                        "  \"brtdy\": \"19800102\",\n" +
                        "  \"sexDiv\": \"M\",\n" +
                        "  \"celphNo\": \"01099990001\",\n" +
                        "  \"mrktYn\": \"Y\",\n" +
                        "  \"mrktDate\": \"20200824155819\",\n" +
                        "  \"mrktCd\": \"1111\"\n" +
                        "}";
                MYP_0001.Response sample = new Gson().fromJson(test, MYP_0001.Response.class);
                if(sample!=null) {
                    ui.setData(sample);
                    ui.cbAd.setChecked(sample.getMrktYn().equalsIgnoreCase("Y") ? true : false);
                    ui.cbSms.setChecked(sample.getMrktCd().substring(0,1).equalsIgnoreCase("1") ? true : false);
                    ui.cbEmail.setChecked(sample.getMrktCd().substring(1,2).equalsIgnoreCase("1") ? true : false);
                    ui.cbPhone.setChecked(sample.getMrktCd().substring(3,4).equalsIgnoreCase("1") ? true : false);
                    ui.cbAll.setChecked(ui.cbEmail.isChecked()&&ui.cbSms.isChecked()&&ui.cbPhone.isChecked());
                }


                switch (result.status){
                    case SUCCESS:
//                        if(result.data!=null) {
//                            ui.setData(result.data);
//                            ui.cbAd.setChecked(result.data.getMrktYn().equalsIgnoreCase("Y") ? true : false);
//                            ui.cbSms.setChecked(result.data.getMrktCd().substring(0,1).equalsIgnoreCase("1") ? true : false);
//                            ui.cbEmail.setChecked(result.data.getMrktCd().substring(1,2).equalsIgnoreCase("1") ? true : false);
//                            ui.cbPhone.setChecked(result.data.getMrktCd().substring(3,4).equalsIgnoreCase("1") ? true : false);
//                            ui.cbAll.setChecked(ui.cbEmail.isChecked()&&ui.cbSms.isChecked()&&ui.cbPhone.isChecked());
//                        }
                        break;
                    case LOADING:

                        break;
                    case ERROR:

                        break;
                }


            }
        });
        mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG_GA01.getId()));
        //TODO 처리 필요
        ui.setActivity(this);
        ui.cbEmail.setOnCheckedChangeListener(listener);
        ui.cbPhone.setOnCheckedChangeListener(listener);
        ui.cbSms.setOnCheckedChangeListener(listener);
        ui.cbAll.setOnCheckedChangeListener(listenerAll);
        ui.vBlock.setOnTouchListener((view, motionEvent) -> true);
    }

    @Override
    public void onSingleClick(View v) {
        switch (v.getId()){
            case R.id.iv_arrow:
                if(ui.tvAdInfo.getVisibility()==View.VISIBLE){
                    ui.ivArrow.setImageResource(R.drawable.btn_accodian_open);
                    collapse(ui.tvAdInfo);
                }else{
                    ui.ivArrow.setImageResource(R.drawable.btn_accodian_close);
                    expand(ui.tvAdInfo);
                }
                break;

//            case R.id.cb_email:
//            case R.id.cb_sms:
//            case R.id.cb_phone:
//                setAllCheckBox();
//                break;
//            case R.id.cb_all:
//
//                Log.v("checkbox","checkbox:"+ui.cbAll.isChecked());
//                break;
        }
    }

    CompoundButton.OnCheckedChangeListener listener = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            ui.cbAll.setChecked(ui.cbEmail.isChecked()&&ui.cbPhone.isChecked()&&ui.cbSms.isChecked());
        }
    };

    CompoundButton.OnCheckedChangeListener listenerAll = (compoundButton, b) -> {
        if(compoundButton.isPressed()) {
            ui.cbEmail.setChecked(b);
            ui.cbPhone.setChecked(b);
            ui.cbSms.setChecked(b);
        }
    };

    /**
     * 2019-07-25 park
     * View visible 시 expand 애니메이션 효과
     * @param v 대상 view 객체
     */
    public static void expand(final View v) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        final int targtetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 0;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1 ? ViewGroup.LayoutParams.WRAP_CONTENT
                        : (int)(targtetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    /**
     * 2019-07-25 park
     * View gone 시 collapse 애니메이션 효과
     * @param v 대상 view 객체
     */
    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }
}
