package com.genesis.apps.ui.main;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemServiceNetworkPopupBinding;

import androidx.annotation.NonNull;

public class ServiceNetworkPopUpView {

    private final int REMOVE_POPUP=1001;
    private ItemServiceNetworkPopupBinding ui;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if(ui!=null&&ui.tvPopup!=null){
                Animation animation = new AlphaAnimation(1, 0);
                animation.setDuration(500);
                ui.tvPopup.setVisibility(View.GONE);
                ui.tvPopup.setAnimation(animation);
            }

        }
    };

    public ServiceNetworkPopUpView(ItemServiceNetworkPopupBinding ui) {
        this.ui = ui;
    }

    public void showPopUp(BtrVO btrVO, int authNm){
        if(btrVO==null|| StringUtil.isValidString(btrVO.getAcps1Cd()).equalsIgnoreCase("2"))
            return;

        String msg;

        switch (authNm){
            case R.string.bt06_17://차체도장
                msg = btrVO.getPntgXclSvcSbc();
                break;
            case R.string.bt06_18://기술력우수
                msg = btrVO.getPrimTechSvcSbc();
                break;
            case R.string.bt06_23://cs우수인증
                msg = btrVO.getPrimCsSvcSbc();
                break;
            default:
                return;
        }

        if(!TextUtils.isEmpty(msg)) {
            handler.removeMessages(REMOVE_POPUP);
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(500);
            ui.tvPopup.setVisibility(View.VISIBLE);
            ui.tvPopup.setAnimation(animation);
            ui.tvPopup.setText(msg);
            handler.sendEmptyMessageDelayed(REMOVE_POPUP, 5000);
        }
    }

}
