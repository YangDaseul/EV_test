package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_2005;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.VibratorUtil;
import com.genesis.apps.databinding.ActivityMygMembershipCardPasswordBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import androidx.lifecycle.ViewModelProvider;

public class MyGMembershipCardPasswordActivity extends SubActivity<ActivityMygMembershipCardPasswordBinding> {

    private MYPViewModel mypViewModel;
    private final int STEP_1_1 = 1;
    private final int STEP_1_2 = 2;
    private final int STEP_2_1 = 3;
    private final int STEP_2_2 = 4;
    private final int STEP_2_3 = 5;
    private int step = STEP_1_1;
    private ImageView[] ivList;

    private String newPwd = "";
    private String currPwd = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_card_password);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        ivList = new ImageView[]{ui.ivInput1, ui.ivInput2, ui.ivInput3, ui.ivInput4};
        ui.etHidden.addTextChangedListener(textWatcher);
        setView(STEP_1_1);
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_2005().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    //TODO  종료 및 이전 액티비티에서 알림메시지가 활성화 될 수 있도록 처리 필요
                    break;
                case LOADING:
                    showProgressDialog(true);
                    break;
                case ERROR:
                    showProgressDialog(false);
                    //TODO 에러메시지 확인 및 처리 필요
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.l_input:
                SoftKeyboardUtil.showKeyboard(this);
                break;
        }
    }

    private void setView(int step) {
        this.step = step;
        switch (step) {
            case STEP_1_1:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.tvMsg1.setText(R.string.mg_member03_2);
                ui.ivInput1.setImageResource(R.drawable.ic_password);
                ui.ivInput2.setImageResource(R.drawable.ic_password);
                ui.ivInput3.setImageResource(R.drawable.ic_password);
                ui.ivInput4.setImageResource(R.drawable.ic_password);
                ui.tvMsgError.setVisibility(View.INVISIBLE);
                break;
            case STEP_1_2:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.tvMsg1.setText(R.string.mg_member03_2);
                ui.ivInput1.setImageResource(R.drawable.ic_password_error);
                ui.ivInput2.setImageResource(R.drawable.ic_password_error);
                ui.ivInput3.setImageResource(R.drawable.ic_password_error);
                ui.ivInput4.setImageResource(R.drawable.ic_password_error);
                ui.tvMsgError.setVisibility(View.VISIBLE);
                ui.tvMsgError.setText(R.string.mg_member03_3);
                VibratorUtil.makeMeShake(ui.tvMsgError, 20, 5);
                break;
            case STEP_2_1:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.tvMsg1.setText(R.string.mg_member03_4);
                ui.ivInput1.setImageResource(R.drawable.ic_password);
                ui.ivInput2.setImageResource(R.drawable.ic_password);
                ui.ivInput3.setImageResource(R.drawable.ic_password);
                ui.ivInput4.setImageResource(R.drawable.ic_password);
                ui.tvMsgError.setVisibility(View.INVISIBLE);
                break;
            case STEP_2_2:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.tvMsg1.setText(R.string.mg_member03_5);
                ui.ivInput1.setImageResource(R.drawable.ic_password);
                ui.ivInput2.setImageResource(R.drawable.ic_password);
                ui.ivInput3.setImageResource(R.drawable.ic_password);
                ui.ivInput4.setImageResource(R.drawable.ic_password);
                ui.tvMsgError.setVisibility(View.INVISIBLE);
                break;
            case STEP_2_3:
                ui.etHidden.setTag(R.id.et_reject, true);
                ui.etHidden.setText("");
                ui.tvMsg1.setText(R.string.mg_member03_4);
                ui.ivInput1.setImageResource(R.drawable.ic_password_error);
                ui.ivInput2.setImageResource(R.drawable.ic_password_error);
                ui.ivInput3.setImageResource(R.drawable.ic_password_error);
                ui.ivInput4.setImageResource(R.drawable.ic_password_error);
                ui.tvMsgError.setVisibility(View.VISIBLE);
                ui.tvMsgError.setText(R.string.mg_member03_6);
                VibratorUtil.makeMeShake(ui.tvMsgError, 20, 5);
                break;
            default:
                break;
        }
    }


    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (Boolean.parseBoolean(ui.etHidden.getTag(R.id.et_reject).toString())) {
                ui.etHidden.setTag(R.id.et_reject, false);
                return;
            }

            if (charSequence.length() == ivList.length) {
                switch (step) {
                    case STEP_1_1:
                    case STEP_1_2:
                        currPwd = charSequence.toString();
                        setView(STEP_2_1);
                        return;
                    case STEP_2_1:
                    case STEP_2_3:
                        newPwd = charSequence.toString();
                        setView(STEP_2_2);
                        return;
                    case STEP_2_2:
                        if (newPwd.equalsIgnoreCase(charSequence.toString())) {
                            //신규비밀번호가 일치하는 경우
                            mypViewModel.reqMYP2005(new MYP_2005.Request(APPIAInfo.MG_MEMBER03.getId(), newPwd));
                            break;
                        } else {
                            //일치하지 않는 경우
                            newPwd = "";
                            setView(STEP_2_3);
                            return;
                        }
                    default:
                        break;
                }
            } else {
                switch (step) {
                    case STEP_1_2:
                    case STEP_2_3:
                        ui.tvMsgError.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            for (int pos = 0; pos < ivList.length; pos++) {
                if (pos < charSequence.length()) {
                    ivList[pos].setImageResource(R.drawable.ic_password_input);
                } else {
                    ivList[pos].setImageResource(R.drawable.ic_password);
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}
