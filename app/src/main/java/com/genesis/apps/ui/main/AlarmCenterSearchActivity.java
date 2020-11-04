package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityAlarmCenterSearchBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class AlarmCenterSearchActivity extends SubActivity<ActivityAlarmCenterSearchBinding> {

    private CMNViewModel cmnViewModel;
    private AlarmCenterRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_center_search);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
    }

    private void initView() {
        adapter = new AlarmCenterRecyclerAdapter(onSingleClickListener);
        ui.lSearchParent.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.lSearchParent.rv.setHasFixedSize(true);
        ui.lSearchParent.rv.setAdapter(adapter);

        ui.lSearchParent.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                reqListData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        ui.lSearchParent.etSearch.setOnFocusChangeListener((view, hasFocus) -> {

            if (hasFocus) {
                reqListData(ui.lSearchParent.etSearch.getText().toString());
            } else {
                SoftKeyboardUtil.hideKeyboard(AlarmCenterSearchActivity.this, getWindow().getDecorView().getWindowToken());
            }

        });

        //키보드에서 search 버튼 클릭할 경우 정의 스토리보드에 정의되어있지 않아 삭선처리
//        ui.etSearch.setOnEditorActionListener(editorActionListener);
    }

    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {
    }

    @Override
    public void getDataFromIntent() {

    }

    private void setListView(List<NotiInfoVO> list) {
        if (list == null || list.size() < 1) {
            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.lSearchParent.tvEmpty.setVisibility(View.GONE);
        }
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }


    private void reqListData(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            ui.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_dadde3);
            ui.lSearchParent.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.lSearchParent.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_141414);
            try {
                setListView(cmnViewModel.getNotiInfoFromDB("", "%"+keyword+"%"));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }

//    /**
//     * @brief 키보드에서 search 버튼 클릭할 경우 정의
//     * (스토리보드에 정의되어 있지 않아 제거)
//     *
//     */
//    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
//        if(actionId== EditorInfo.IME_ACTION_SEARCH){
//            String search = textView.getText().toString();
//            if (!TextUtils.isEmpty(search)) {
//                MenuVO menuVO = new MenuVO();
//                menuVO.setName(search);
//                menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_ADD_MENU, menuVO); //키보드 검색 버튼 누를 경우 최근 검색어 저장
//            }
//        }
//        return false;
//    };

}
