package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.main.myg.MenuRepository;
import com.genesis.apps.comm.model.main.myg.MenuViewModel;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.databinding.ActivityMygMenuBinding;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.ui.common.activity.IntroActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listview.ExtncPlanPontAdapter;
import com.genesis.apps.ui.common.view.listview.MenuAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MyGMenuActivity extends SubActivity<ActivityMygMenuBinding> {

    private MenuViewModel menuViewModel;
    private MenuAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_menu);
        ui.setLifecycleOwner(this);

        menuViewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        menuViewModel.getMenuList().observe(this, result -> {
            adapter.setRecently(false);
            ui.tvTitleSub.setText("전체메뉴");
            setListView(result.data);
        });
        menuViewModel.getRecentlyMenuList().observe(this, result -> {
            adapter.setRecently(true);
            ui.tvTitleSub.setText(R.string.mg00_word_2);
            setListView(result.data);
        });
        menuViewModel.getKeywordMenuList().observe(this, result -> {
            adapter.setRecently(false);
            ui.tvTitleSub.setText(R.string.mg00_word_3);
            setListView(result.data);
        });

        adapter = new MenuAdapter((v, position) -> {

            switch (v.getId()) {
                case R.id.btn_del:
                    //최근 검색어 삭제버튼 클릭 시
                    menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_REMOVE_MENU, adapter.getItem(position));
                    break;
                default:
                    if (adapter.isRecently()) {
                        //최근 검색어 리스트 항목 클릭 시 editbox에 자동으로 타이핑해줌
                        menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_ADD_MENU, adapter.getItem(position)); //이미 리스트가 있지만 최상단으로 올려주는 효과
                        String name = ((MenuVO) adapter.getItem(position)).getName();
                        ui.etSearch.setText(name);
                        ui.etSearch.setSelection(name.length());
                    } else {
                        menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_ADD_MENU, adapter.getItem(position)); //키워드 저장
                        if (((MenuVO) adapter.getItem(position)).getActivity() == null) {
                            SnackBarUtil.show(this, "페이지가 존재하지 않습니다.");
                        } else {
                            startActivitySingleTop(new Intent(this, ((MenuVO) adapter.getItem(position)).getActivity()), 0);
                            finish();
                        }
                    }
                    break;
            }
        });
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);

        ui.etSearch.addTextChangedListener(new TextWatcher() {
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


        ui.etSearch.setOnFocusChangeListener((view, hasFocus) -> {

            if (hasFocus) {
                reqListData(ui.etSearch.getText().toString());
            } else {
                SoftKeyboardUtil.hideKeyboard(MyGMenuActivity.this);
            }

        });

        //키보드에서 search 버튼 클릭할 경우 정의 스토리보드에 정의되어있지 않아 삭선처리
//        ui.etSearch.setOnEditorActionListener(editorActionListener);
        menuViewModel.reqMenuList(); //전체 리스트 요청
    }

    @Override
    public void onSingleClick(View v) {

    }

    private void setListView(List<MenuVO> list) {
        if (list == null || list.size() < 1) {
            ui.tvEmpty.setVisibility(View.VISIBLE);
        } else {
            ui.tvEmpty.setVisibility(View.GONE);
        }
        adapter.setRows(list);
        adapter.notifyDataSetChanged();
    }


    private void reqListData(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_GET_MENU_ALL, null); //최근 검색어
            ui.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_dadde3);
        } else {
            MenuVO menuVO = new MenuVO();
            menuVO.setName(keyword);
            menuViewModel.reqKeywordMenuList(menuVO); //검색결과
            ui.etSearch.setBackgroundResource(R.drawable.bg_ffffff_stroke_141414);
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
