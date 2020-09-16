package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
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
import com.genesis.apps.databinding.ActivityMygMenuBinding;
import com.genesis.apps.room.DatabaseHolder;
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

            switch (v.getId()){
                case R.id.btn_del:
                    menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_REMOVE_MENU, adapter.getItem(position)); //삭제버튼 클릭 시..
                    break;
                default:
                    if(adapter.isRecently()) {//todo 검색어 목록일 경우 항목 클릭 시  edit 박스에 자동 입력 됨
                        ui.etSearch.setText( ((MenuVO)adapter.getItem(position)).getName()  );
                        //검색어에 자동 타이핑..
                    }else{
                        //TODO 페이지 이동 -> class를 Menuvo에 추가하고. 이그노어 처리해야함

                        // todo 클릭하면 키워드를 저장하고  ->
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
                MenuVO menuVO = new MenuVO();
                menuVO.setName(s.toString());

                if(TextUtils.isEmpty(s.toString())){
                    menuViewModel.reqRecentlyMenuList(MenuRepository.ACTION_GET_MENU_ALL, menuVO); //최근 검색어
                }else{
                    menuViewModel.reqKeywordMenuList(menuVO); //검색결과
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        menuViewModel.reqMenuList();

    }



    @Override
    public void onSingleClick(View v) {

    }

    private void setListView(List<MenuVO> list){
        if(list==null||list.size()<1){
            ui.tvEmpty.setVisibility(View.VISIBLE);
        }else{
            ui.tvEmpty.setVisibility(View.GONE);
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        }
    }



    public void search(final String search, TextView empty) {

        this.search = search;

        if (TextUtils.isEmpty(search)) {
            addAllItem(dataRecently);
            empty.setVisibility(View.GONE);
            layoutRecently.setVisibility(dataRecently.size()>0 ? View.VISIBLE : View.GONE);
        } else {
            ArrayList<DummyVehicle> temps = new ArrayList<DummyVehicle>();

            if (dataOriginal != null && dataOriginal.size() > 0) {
                for (int i = 0; i < dataOriginal.size(); i++) {
                    DummyVehicle info = dataOriginal.get(i);
                    String[] compares = {info.vrn,info.vin,info.brand,info.code};
                    for (String compare : compares) {
                        if (!TextUtils.isEmpty(compare)) {
                            boolean hasSearch = SoundSearcher.matchString(compare.toLowerCase(), search.toLowerCase());
                            if (hasSearch) {
                                temps.add(info);
                                break;
                            }
                        }
                    }
                }
            }
            addAllItem(temps);
            empty.setVisibility(temps.size()<1 ? View.VISIBLE : View.GONE);
            layoutRecently.setVisibility(View.GONE);
        }
        //notifyDataSetChanged();


        notifyItemRangeChanged(0, getItemCount());
    }






}
